package other;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.*;

public class TranslateTest {
	public static void main(String[] args){

		try {
			String translation = Translate.execute("hello", Language.ENGLISH, Language.GERMAN);
			System.out.println(translation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
