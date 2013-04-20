/**
 * 
 */
package nlptools.lexicalization;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import org.tartarus.snowball.ext.englishStemmer;

/**
 * @author Miltos Allamanis <m.allamanis@ed.ac.uk>
 * 
 */
public class KeyPhraseChunkExtractor {

	final TokenizerModel nlTokenizerModel;
	final Tokenizer nlTokenizer;
	final POSModel posModel;
	final POSTaggerME tagger;
	final ChunkerModel chunkModel;
	final ChunkerME chunker;
	final englishStemmer stemmer = new englishStemmer();

	public final String[] nonChars = { "/", "\\", "<", ">", ".", ":", "(", ")",
			"#" };

	public KeyPhraseChunkExtractor() throws Exception, IOException {

		InputStream modelIn = getClass().getResourceAsStream(
				"/nlptools/data/en-pos-maxent.bin");
		posModel = new POSModel(modelIn);
		tagger = new POSTaggerME(posModel);

		modelIn = getClass().getResourceAsStream(
				"/nlptools/data/en-chunker.bin");
		chunkModel = new ChunkerModel(modelIn);
		chunker = new ChunkerME(chunkModel);

		modelIn = getClass().getResourceAsStream("/nlptools/data/en-token.bin");
		nlTokenizerModel = new TokenizerModel(modelIn);
		nlTokenizer = new TokenizerME(nlTokenizerModel);
	}

	public List<String> getPhraseChunks(final String sentence) {
		String processedSentence = sentence;
		for (final String oddChar : nonChars) {
			processedSentence = processedSentence.replace(oddChar, "");
		}
		String[] toks = nlTokenizer.tokenize(checkNotNull(processedSentence));
		String tags[] = tagger.tag(toks);

		final Span chunkSpan[] = chunker.chunkAsSpans(toks, tags);
		final List<String> chunks = new ArrayList<String>();
		for (final Span s : chunkSpan) {
			if (s.getType().equals("NP"))
				continue;

			String chunk = "";
			for (int i = s.getStart(); i < s.getEnd(); i++) {
				if (tags[i].contains("VB")) {
					stemmer.setCurrent(toks[i]);
					stemmer.stem();
					final String stemmedWord = stemmer.getCurrent()
							.toLowerCase();
					chunk += stemmedWord + "_";
				} else {
					chunk += toks[i].toLowerCase() + "_";
				}
			}
			chunks.add(chunk.substring(0, chunk.length() - 1));
		}
		return chunks;
	}
}
