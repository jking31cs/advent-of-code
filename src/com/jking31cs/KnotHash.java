package com.jking31cs;

public class KnotHash {
    static int[] inputAdd = new int[] {17, 31, 73, 47, 23};
    public static void knotHash(int[] myArray, String input) {
        int skip = 0;
        int index = 0;
        int myArrayLength = myArray.length;

        int[] lengths = new int[input.length() + 5];
        char[] inputArr = input.toCharArray();
        for (int i = 0; i < input.length(); i++) {
            lengths[i] = (int) inputArr[i];
        }
        for (int i = 0; i < 5; i++) {
            lengths[input.length()+i] = inputAdd[i];
        }
        for (int k = 0; k < 64; k++) {
            for (int length : lengths) {
                //First get the sublist
                int[] newSubList = new int[length];
                for (int i = index; i < length+index; i++) {
                    newSubList[i-index] = myArray[(i)%myArrayLength];
                }
                //Reverse the sublist
                for (int i=0,j=length-1; i<(length/2); i++,j--) {
                    int temp = newSubList[i];
                    newSubList[i] = newSubList[j];
                    newSubList[j] = temp;
                }
                //Apply to original array
                for (int i = index; i < index + length; i++) {
                    myArray[i%myArrayLength] = newSubList[i-index];
                }
                //Update index
                index += length + skip;
                skip+=1;
            }
        }

    }

    public static void main(String[] args) {
        int[] myArray = new int[256];
        for (int i = 0; i < 256; i++) myArray[i] = i;
        String input = "88,88,211,106,141,1,78,254,2,111,77,255,90,0,54,205";
//        String input = "AoC 2017";
        knotHash(myArray, input);

        int[] denseHash = getDenseHash(myArray);
        String toRet = "";
        for (int i = 0; i < denseHash.length; i++) {
            toRet = toRet + (Integer.toHexString(denseHash[i]));
        }
        System.out.println(toRet);
    }

    private static int[] getDenseHash(int[] myArray) {
        int[] toRet = new int[16];

        for (int index = 0; index < 16; index++) {
            int begin = index * 16;
            int value = myArray[begin];
            for (int i = 1; i < 16; i++) {
                value = value ^ myArray[begin+i];
            }
            toRet[index] = value;
        }
        return toRet;
    }
}
