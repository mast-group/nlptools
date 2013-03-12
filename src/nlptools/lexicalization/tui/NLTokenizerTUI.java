/**
 * 
 */
package nlptools.lexicalization.tui;

import java.io.File;
import java.io.IOException;

import nlptools.lexicalization.KeyPhraseChunkExtractor;
import nlptools.lexicalization.WordTokenRetriever;

import org.apache.commons.io.FileUtils;

/**
 * @author Miltos Allamanis <m.allamanis@ed.ac.uk>
 * 
 */
public class NLTokenizerTUI {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage <filepath> tokens|keyphrases");
			return;
		}
		final String text = FileUtils.readFileToString(new File(args[0]));

		if (args[1].equals("tokens")) {
			final WordTokenRetriever wr = new WordTokenRetriever();
			System.out.println(wr.getTokensFrom(text));
		} else {
			final KeyPhraseChunkExtractor kpe = new KeyPhraseChunkExtractor();
			System.out.println(kpe.getPhraseChunks(text));
		}
	}

}
