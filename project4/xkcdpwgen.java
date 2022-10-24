import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class xkcdpwgen {
  public static void main(String[] args) throws IOException {
    int words = 4;
    int caps = 0;
    int numbers = 0;
    int symbols = 0;


    for (int i = 0; i < args.length; i++) {
      String instr = args[i];

      if (instr.equals("-w") || instr.equals("--words")) {
        words = Integer.parseInt(args[i + 1]);
      } else if (instr.equals("-c") || instr.equals("--caps")) {
        caps = Integer.parseInt(args[i + 1]);
      } else if (instr.equals("-n") || instr.equals("--numbers")) {
        numbers = Integer.parseInt(args[i + 1]);
      } else if (instr.equals("-s") || instr.equals("--symbols")) {
        symbols = Integer.parseInt(args[i + 1]);
      }
    }
    ArrayList<String> wordList = generateList();
    String password = generatePassword(words, caps, numbers, symbols, wordList);

    System.out.println(password);
  }

  private static ArrayList<String> generateList() throws FileNotFoundException {
    ArrayList<String> result = new ArrayList<String>();
    Scanner s = new Scanner(new File("words.txt"));

    while (s.hasNext()) {
      result.add(s.next());
    }
    s.close();

    for (int i = 0; i < result.size(); i++) {
      if (result.get(i).length() < 7) {
        result.remove(i);
        i--;
      }
    }

    return result;
  }

  private static String getWord(ArrayList<String> list) {
    Random rand = new Random();
    int index = rand.nextInt(list.size());
    return list.get(index);
  }

  private static String generatePassword(int w, int c, int n, int s, ArrayList<String> wordList) {
    String result = "";
    Random rand = new Random();
    for (int i = 0; i < w; i++) {
      result = getWord(wordList) + result;
      if (c > 0) {
        result = result.substring(0,1).toUpperCase() + result.substring(1);
        c--;
      }
      if (n > 0) {
        int num = rand.nextInt(10);
        result = Integer.toString(num) + result;
        n--;
      }
      if (s > 0) {
        String sym = "~!@#$%^&*.:;";
        int x = rand.nextInt(sym.length());
        result = sym.charAt(x) + result;
        s--;
      }
    }

    return result;
  }


}