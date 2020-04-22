public class Testing {
    public static void main(String[] args) {
        String temp = "Hello";
        temp += "\nworld!";
        System.out.println(temp);

        Long[] list = new Long[1];
        list[0] = 0b010001L;
        System.out.println(Long.valueOf(list[0]));

        Long test = Long.parseLong("010001", 2);
        String output = "";
        String binary = Long.toBinaryString(test);
        for (int i = binary.length(); i < 8; i++)
            output += "0";
        output += binary;
        System.out.println(output);
    }
}