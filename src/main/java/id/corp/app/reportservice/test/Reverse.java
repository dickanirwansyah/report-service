package id.corp.app.reportservice.test;

public class Reverse {

    public static void main(String[] args){
        new Reverse().reverseUsingStrBuilder("dickanirwansyah");
        new Reverse().reverseUsingGetBytes("dickanirwansyah");
    }

    /** using get bytes **/
    private String reverseUsingGetBytes(String input){
        byte[] dataByte = input.getBytes();
        byte[] dataResult = new byte[dataByte.length];

        for (int i=0; i < dataByte.length; i++){
            dataResult[i] = dataByte[dataByte.length - i - 1];
        }
        System.out.println(new String(dataResult));
        return new String(dataResult);
    }

    /** using string builder **/
    private String reverseUsingStrBuilder(String input){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(input);
        stringBuilder.reverse();
        System.out.println("result reverse --> "+stringBuilder.toString());
        return stringBuilder.toString();
    }
}
