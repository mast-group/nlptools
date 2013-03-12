package nlptools.lexicalization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

import org.tartarus.snowball.ext.englishStemmer;

/**
 * 
 */

/**
 * @author Miltos Allamanis <m.allamanis@ed.ac.uk>
 * 
 */
public class WordTokenRetriever {

	final static List<String> stopwords = Arrays
			.asList(".,?,(,),*,&,^,%,$,',\",<,>,a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,need,neither,no,nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,use,wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your"
					.split(","));

	final static String[] spChar = { "(", ")", "\"", "\\", "/", "'", "<", "?",
			">", ".", ",", "|", "=", ":", "-", "}", "{", "[", "]", "^" };

	final TokenizerModel nlTokenizerModel;
	final Tokenizer nlTokenizer;
	final englishStemmer stemmer = new englishStemmer();

	public WordTokenRetriever() throws InvalidFormatException, IOException {
		final InputStream modelIn = new FileInputStream(
				"/home/miltiadis/workspace/MSRChallenge/deps/en-token.bin"); // TODO
		nlTokenizerModel = new TokenizerModel(modelIn);
		nlTokenizer = new TokenizerME(nlTokenizerModel);
	}

	/**
	 * Get the stemmed and tokenized list of words from a given text.
	 * 
	 * @param text
	 * @return
	 */
	public List<String> getTokensFrom(final String text) {
		final List<String> words = new ArrayList<String>();
		final String[] bodyTokens = nlTokenizer.tokenize(text);
		for (final String word : bodyTokens) {
			if (isStopWord(word)) {
				continue;
			}
			stemmer.setCurrent(word);
			stemmer.stem();
			final String stemmedWord = stemmer.getCurrent().toLowerCase();
			if (isStopWord(stemmedWord)) {
				continue;
			}
			if (stemmedWord != null && stemmedWord.length() > 1) {
				words.add(stemmedWord);
			}
		}
		return words;
	}

	/**
	 * Check if a word is a stopword.
	 * 
	 * @param word
	 * @return
	 */
	private boolean isStopWord(final String word) {
		if (stopwords.contains(word))
			return true;

		for (final String spec : spChar) {
			if (word.contains(spec))
				return true;
		}
		return false;
	}

}
