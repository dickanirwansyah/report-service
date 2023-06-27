package id.corp.app.reportservice.test;

public class Palindrome {

    public static void main(String[] args){
        new Palindrome().palindrome("kasur ini rusak");
    }

    /** using substring **/
    private void palindrome(String inputText){
        String data = "";
        for (int i = inputText.length(); i > 0; i--){
            String dataTest = inputText.substring(i-1, i);
            data += dataTest;
        }

        if (inputText.equals(data)){
            System.out.println("----> "+inputText+ " --> adalah Palindrome");
        }else {
            System.out.println("----> "+inputText+ "---> bukan palindrome");
        }
    }
}
