/*
Question1:
-----------------------------------------------------------------------------------------------------
Consider the problem of neatly printing a paragraph on a printer. The input text is
a sequence on n words of lengths l1,l2,l3,…,ln measured in characters. We want to
print this paragraph neatly on a number of lines that hold a maximum of M
characters each. Our criterion of  neatness as follows. If a given line contains
words i through j , where i ≤ j , and we leave exactly one space between words, the
number of extra space characters at the end of the line  which
must be nonnegative so that the words fit on the line. We wish to minimize the sum,
over all lines except the last, of the cubes of the number of the extra space characters
at the ends of lines. Give a dynamic-programming algorithm to print a paragraph
of n words nearly on a printer. Analyze the running time and space requirement of
your algorithm.
*/
/*
Solution :
-----------------------------------------------------------------------------------------------------
In the question we need to find a dynamic programming solution.In hear i choose an bottom
up approch to find a solution.First we need to create a cost matrix to solve this question.
we fill the matrix by finding the cube of the value left when filling the one line size with
words from vertical side to horizontal side.if values are nagative we fill them with max intiger
value.
next part is to print the string.We do this by finding the last word of the string array and
calculating backwords while filling the line with maximum amount of words to minimize the white
space.When going upwords if the previous configaration is not the most suitable one then we change it
to the most suitable one.We can use this formulae for this.
minCost[i] = minCost[j] + cost[i][j-1]

**As we use an n by n matrix the Space complexity is O(n^2)
 Space complexity is O(n^2)
**Maximum time is taken by the two for loops whitch is O(n^2) so
 Time complexity is O(n^2)
 Refer the report for more detailed version.

/*
Dependancies :
--------------------------------------------------------------------------------------------------------
compiler: javac 1.8.0_141
*/

/*
Test cases
---------------------------------------------------------------------------------------------------------

s1={"Whenever", "it","encounters","a","string","literal" ,"in" ,"your", "code", "the" ,"compiler" ,"creates" ,"a" ,"String" ,"object" ,"with" ,"its" ,"value" ,"in", "this" ,"case", "Hello" ,"world"}
s2 = {"we","likes","to","write","code","at","our", "free", "time"};
*/
public class q1{

    public void justify(String words[], int width) {

        int cost[][] = new int[words.length][words.length];// cost matrix initialization(constant time)

        // used to calculate cost value when putting words to a row
        for(int i=0 ; i < words.length; i++){
            cost[i][i] = width - words[i].length();          //O(n^2)
            for(int j=i+1; j < words.length; j++){
                cost[i][j] = cost[i][j-1] - words[j].length() - 1;
            }
        }
        // Print the array for testing
        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost[i].length; j++) {
                System.out.print(cost[i][j] + " ");
                }
            System.out.println();
            }

        System.out.println("\n");

        // fill the positive values(valid spaces) with there cubes to further increase the gap
        // put the negative values to there maximum value(to symbolize infinity)
        for(int i=0; i < words.length; i++){
            for(int j=i; j < words.length; j++){            //O(n^2)
                if(cost[i][j] < 0){
                    cost[i][j] = Integer.MAX_VALUE;// we take max value for infinity
                }else if(j==width && cost[i][j] >=0){//for the last line
                    cost[i][j]=0;
                }else{
                    cost[i][j] = (int)(cost[i][j]*cost[i][j]*cost[i][j]);// else put the cube
                }
            }
        }


        // Print the array for testing

        for (int i = 0; i < cost.length; i++) {
            for (int j = 0; j < cost[i].length; j++) {
                System.out.print(cost[i][j] + " ");
                }
            System.out.println();
            }


        //minCost from i to len is found by trying
        //j between i to len and checking which
        //one has min value
        int minCost[] = new int[words.length];
        int result[] = new int[words.length];
        for(int i = words.length-1; i >= 0 ; i--){
            minCost[i] = cost[i][words.length-1];
            result[i] = words.length;
            for(int j=words.length-1; j > i; j--){      //O(n^2)
                if(cost[i][j-1] == Integer.MAX_VALUE){
                    continue;
                }
                if(minCost[i] > minCost[j] + cost[i][j-1]){
                    minCost[i] = minCost[j] + cost[i][j-1];
                    result[i] = j;
                }
            }
        }
        int i = 0;
        int j;
        System.out.println("\n");
        System.out.println("Minimum cost(cubic value) is " + minCost[0]);
        System.out.println("\n");
        //finally put all words with new line added in
        //string buffer and print it.

        do{
            j = result[i];
            int size=0;
            for(int k=i; k < j; k++){
                System.out.print(words[k] + " ");       //O(n^2)
                size=size+words[k].length() +1 ;}

        for(int p=0;p<width - size +1;p++){ System.out.print(" ");}// this part is for the clearity of the result not a part of the algorithm

        System.out.print("Space kept: "+(width -size +1));


            System.out.print("\n");
            i = j;
        }while(j < words.length);


    }

    public static void main(String args[]){
        String words1[] = {"we","likes","to","write","code","at","our", "free", "time"};
        String words2[] = {"Whenever", "it","encounters","a","string","literal" ,"in" ,"your", "code", "the" ,"compiler" ,"creates" ,"a" ,"String" ,"object" ,"with" ,"its" ,"value" ,"in", "this" ,"case", "Hello" ,"world"};
        q1 object = new q1();
       // object.justify(words2, 12);
      //  object.justify(words2, 25);           //constant time
        object.justify(words1, 6);
      //  object.justify(words1, 12);           //comment out the unwanted test cases






    }
}
