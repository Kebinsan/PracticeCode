/* @author Kevin Taylor
 * Last edited: 10/18/2019
 *
 * The following program is a translator between the english language and morse code. The program will prompt the user
 * to choose whether they wish to translate from english to morse code, translate morse code to english, or exit the program.
 *
 * To translate from english to morse code, the user enters a text string input and the program will translate the input into morse
 * code with single spacing between each character, a '\' character as a space between words, and a '%' character for any 
 * character that could not be translated into morse code. 
 *
 * To translate from morse code to english, the user enters a morse code string with the following rules; single spacing between each 
 * character, add a '|' or '\' character as a space between words. The code then translates the entered morse code and outputs
 * the english text string, any morse character that could not be translated will be set to the '%' character.
 */
import javax.swing.JOptionPane;
 
public class MorseCodeTranslatingProgram {
   public static void main(String[] args) {
      //All English characters translatable into morse code in ASCII order.
      char[] englishCharacters = new char[] { '!', '"', '$', '&', '\'', '(', ')', '+', 
                                              ',', '-', '.', '/', '0', '1', '2', '3', 
                                              '4', '5', '6', '7', '8', '9', ':', ';', 
                                              '=', '?', '@', 'A', 'B', 'C', 'D', 'E', 
                                              'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
                                              'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 
                                              'V', 'W', 'X', 'Y', 'Z', '_' };
      //Parallel to English characters, morse code assigned to each English character.
      String[] morseCode = new String[] { "-.-.--", ".-..-.", "...-..-", ".-...", ".----.", 
                                          "-.--.", "-.--.-", ".-.-.", "--..--", "-....-", 
                                          ".-.-.-", "--..-.", "-----", ".----", "..---", 
                                          "...--", "....-", ".....", "-....", "--...", 
                                          "---..", "----.", "---...", "-.-.-", "-...-", 
                                          "..--..", ".--.-.", ".-", "-...", "-.-.", "-..", 
                                          ".", "..-.", "--.", "....", "..", ".---", "-.-", 
                                          ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
                                          "...", "-", "..-", "...-", ".--", "-..-", "-.--", 
                                          "--..", "..--.-" };
      runMenu(englishCharacters, morseCode);
   }
   
   /* @param takes the parallel lists of english characters and morse code for each character.
    * The following creates a menu for the user with the choice to either translate from morse code to english or english to morse code,
    * Their selection then runs the appropriate code to translate their text input whether that be in morse or english. The user also has the option to exit the program.
    */
   public static void runMenu (char[] englishCharacters, String[] morseCode) {
      String input = "";
      boolean repeat = true;
      String[] options = new String[] {"Morse Code to English", "English to Morse Code", "Exit"};
      //Repeats the menu until the program is exited.
      do {
         //Provides the user with a choice to either translate morse code to english, english to morse code, or exit the program.
         int optionChoice = JOptionPane.showOptionDialog(null, "What would you like to do?", "Choose Translation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
         //Depending on the users choice, a code is executed to perform the choice of action.
         switch (optionChoice) {
            //Translate morse code to english.
            case 0: 
               do {
                  input = JOptionPane.showInputDialog("Rules:\n1. Single space between each character\n2. Use \'\\\' or \'|\' as a space between words (Treat like a character with single spacing)\n\nEnter morse code you want translated into text: ");
                  if (input == null || input.equals("")) {
                     JOptionPane.showMessageDialog(null, "Text was empty, nothing was translated");
                  }
               }
               while (input == null || input.equals(""));
               JOptionPane.showMessageDialog(null, morseToEnglish(input, englishCharacters, morseCode));
               break;
            //Translate english to morse code.
            case 1:
               do {
                  input = JOptionPane.showInputDialog("Enter text you want translated into morse code: ");
                  if (input == null || input.equals("")) {
                     JOptionPane.showMessageDialog(null, "Text was empty, nothing was translated");
                  }
               }
               while (input == null || input.equals(""));
               JOptionPane.showMessageDialog(null, englishToMorse(input, englishCharacters, morseCode));
               break;
            //Exits the program.
            case 2:
               JOptionPane.showMessageDialog(null, "Ending Program...");
               repeat = false;
               break;
            //In case an error occurs.
            default:
               JOptionPane.showMessageDialog(null, "An error has ocurred");
               break;
         }
      }
      while (repeat);
   }
   
   /* @param takes a users morse code input, the list of characters for it to be translated into, and the parallel list of morse code for each character.
    * The following takes the users morse code input, and translates each morse code split by a space into the appropriate character.
    */
   public static String morseToEnglish (String code, char[] characters, String[] morse) {
      //Creates a list of strings separated by a space, each single string should be morse code that represents one character.
      String[] morseEachCharacter = code.split(" ");
      String textOutput = "If a character could not be found, it is replaced by a '%' character\n\n";
      for (int i = 0; i < morseEachCharacter.length; i++) {
         //Adds a space to the output is the string value is a '\' or a '|'.
         if (morseEachCharacter[i].equals("\\") || morseEachCharacter[i].equals("|")) {
            textOutput += " ";
         }
         else {
            //Searches the morse code character of the iteration and returns either the index found at or a -1 if not found.
            int index = linearSearch(morseEachCharacter[i], morse);
            //Replaces the character with a % if it could not be found within the list of translatable characters
            if (index == -1) {
               textOutput += "%";
            }
            //Adds the appropriate english character for the morse code character of the iteration
            else {
               textOutput += characters[index];
            }
         }
      }
      //@return the translated english text of the morse code entered by the user
      return textOutput;
   }
   
   /* @param takes the morse code that represents one character and the list of morse code characters
    * takes the morse code character and searches the list of morse code characters to find out if it exists. If found the index of the code is returned.
    * If not found a -1 is returned to indicate the morse code did not match any within the list of morse code characters.
    */
   public static int linearSearch (String code, String[] morse) {
      for (int i = 0; i < morse.length; i++) {
         if (code.equals(morse[i])) {
            //@return the index if the morse code was found within the list of morse codes
            return i;
         }
      }
      //@return -1 if the code was not found within list of morse codes
      return -1;          
   }
   
   /* @param takes the users text input, the list of characters available to translate into morse, and the parallel list of morse code for each character.
    * The following translates the users text input into morse code by translating each character into the appropriate morse code string/character.
    */
   public static String englishToMorse (String text, char[] characters, String[] morse) {
      String morseOutput = "The morse code displayed follows these rules:\n1. Single space between each character\n2. \'\\\' means a space between words\n" + 
      "3. % is substituted for characters that couldn't be translated into morse code\n\n";
      for (int i = 0; i < text.length(); i++) {
         //If the character for the current iteration is a space, a '\' is added as a space for the morse code.
         if (text.charAt(i) == ' ') {
            morseOutput += "\\";
         }
         else {
            char searchCharacter = Character.toUpperCase(text.charAt(i));
            int n = characters.length;
            int index = binarySearch (characters, 0, n-1, searchCharacter);
            //If the character for the current iteration is not found, it is replaced with a percentage character.
            if (index == -1) {
               morseOutput += "% ";
            }
            else {
               //Adds the morse code for the character if found for the current interation.
               morseOutput += morse[index] + " ";
            }
         }
      }
      //@returns the morse code of the text entered.
      return morseOutput;
   }
   
   /* @param takes a list of characters, two integer numbers, one with a pointer to the beginning of the list of characters and the 
    * other to the end of the list of characters, and a character that is being searched.
    * The following will take the character being searched and search through the list of characters using a binary search method and returns
    * the index it was found at, a -1 is return if the character could not be found.
    */
   public static int binarySearch (char[] arr, int l, int r, char x) {
      if (r >= l) {
         //Finds the middle index of the list of characters.
         int mid = l + (r - l)/2;
         //Compares the character being searched and the middle character in the list based on ASCII decimal value.
         if (arr[mid] == x) {
            return mid;
         }
         //If the middle character in the list's ASCII decimal value is greater than the ASCII value of the searched character, the lower half of the list is searched.
         if (arr[mid] > x) {
            return binarySearch(arr, l, mid-1, x);
         }
         //Searches the upper half of the list if the middle character ASCII decimal value is less than the ASCII value of the searched character.
         else {
            return binarySearch(arr, mid+1, r, x);
         }
      }
      //@return -1 if the character was not found
      return -1;
   }

}

   
 