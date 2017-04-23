/**
 * Created by yuval_edelman on 4/2/2015.
 */
import java.util.*;
public class BigInt implements Comparable<BigInt> {
    private ArrayList<Integer> big;
    private boolean positive; //number is positive or negative?

    public BigInt(String number) {
        if (number==""){
            throw new IllegalArgumentException("the number is empty");
        }
        if (number==null){
            throw new IllegalArgumentException("no input");
        }
        if (number.contains("+")||number.contains("-")){
            if (!number.substring(1).matches("[0-9]+")){
                throw new IllegalArgumentException("the number is not valid");
            }
        }else{
            if (!number.matches("[0-9]+")){
                throw new IllegalArgumentException("the number is not valid");
            }
        }
        positive=isPositive(number.charAt(0)); //check if the number is positive
        fillList(number);
    }

    private boolean isPositive(char x){
        if(x=='-'){
            return false;
        }
        else {
            return  true;
        }
    }

    private void fillList(String num){
        big=new ArrayList<Integer>(); //make new array list for the number
        int n=0;
        if(num.charAt(0)=='-' || num.charAt(0)=='+'){ //if the number start with "-" or "+"
            for(int i=1;i<num.length();i++){
                n=Character.getNumericValue(num.charAt(i));
                big.add(i-1,n); //adding the digit to the array
            }

        }else{
            for(int i=0;i<num.length();i++){
                n=Character.getNumericValue(num.charAt(i));
                big.add(i,n);
            }
        }
    }

    private String plus (ArrayList<Integer> bigger,ArrayList<Integer>small){
        String result= "";
        int i=bigger.size()-1;
        int j=small.size()-1;
        int dif=i-j; //the difference between the tow number size
        int equal=0;
        int carry=0;

        for (;dif>=0;dif--){
            while (j>=0){
                equal=bigger.get(i)+small.get(j)+carry;
                if (equal > 9) {
                    equal = equal % 10;
                    carry = 1;
                    result = equal + result;
                } else {
                    carry = 0;
                    result = equal + result;
                }
                j--;
                i--;
            }
            if (dif>0) {
                if (carry == 1) {
                    equal = bigger.get(i) + carry;
                    if (equal > 9) {
                        equal = equal % 10;
                        carry = 1;
                        result = equal + result;
                    }else {
                        result = equal + result;
                        carry=0;
                    }
                }else{
                    equal = bigger.get(i) + carry;
                    result = equal + result;
                    carry=0;
                }
                i--;
            }
        }
        if (carry==1){
            result="1"+result;
        }
        return result;
    }

    public BigInt plus(BigInt b){
        String result= "";
        int i,j,equal;
        int carry=0;
        int who;//who is bigger?

        if((positive && b.positive)||(!positive && !b.positive)) { //the two numbers are positive
            if (big.size() >= b.big.size()) {
                result = plus(big, b.big);
            } else {
                result = plus(b.big, big);
            }
        }
        if((positive && !b.positive)&& big.size()==b.big.size()) {
            who=whoIsBigger(b);
            if(who==1){
               result=minus(big,b.big);
            }
            if(who==2){
                result="-"+minus(b.big,big);
            }
        }if (positive && !b.positive&& big.size()!=b.big.size()){
            if (big.size()>b.big.size()){
                result=minus(big,b.big);
            }else {
                result="-"+minus(b.big,big);
            }
        }
        if((!positive && b.positive)&&big.size()==b.big.size()){
            who=whoIsBigger(b);
            if(who==1){
                result="-"+minus(big,b.big);
            }
            if(who==2){
                result=minus(b.big,big);
            }
        }
        if ((!positive && b.positive)&&big.size()!=b.big.size()){
            if (big.size()>b.big.size()){
                result="-"+minus(big,b.big);
            }else{
                result=minus(b.big,big);
            }
        }

        if((!positive && !b.positive)){
            result="-"+result;
        }
        BigInt bb=new BigInt(result);
        return bb;
    }

    private int difference (int b,int s){ //return the difference between big to small number.
        int i=0,c=s;
        while (c!=b){
            i++;
            c+=1;
            if (c==10) {
                c = 0;
            }
        }
       // i++;
        return i;
    }

    public  BigInt minus (BigInt b){
        String result= "";
        int who;//who is bigger?
        if (this.equals(b)) {
            result = "0";
        }else{
            if (positive && b.positive) { //the two numbers are positive
                if (big.size() > b.big.size()) {
                    result = minus(big, b.big);
                }
                if (big.size() < b.big.size()) {
                    result = minus(b.big, big);
                }
                if (big.size() == b.big.size()) {
                    who=whoIsBigger(b);
                    if (who == 1) {
                        result = "+" + minus(big, b.big);
                    }
                    if (who == 2) {
                        result = "-" + minus(b.big, big);
                    }
                    if (who== 0){
                        result="0";
                    }
                }
            }
            if (positive && !b.positive) {
                b.positive=true;
                BigInt bb;
                bb = plus(b);
                b.positive=false;
                return bb;
            }
            if (!positive && b.positive) {
                positive=true;
                BigInt bb;
                bb = plus(b);
                positive=false;
                bb.positive = false;
                return bb;
            }
            if (!positive && !b.positive) {
                if (big.size() > b.big.size()) {
                    result = "-" + minus(big, b.big);
                }
                if (big.size() < b.big.size()) {
                    result = "+" + minus(b.big, big);
                }
                if (big.size() == b.big.size()) {
                    who=whoIsBigger(b);
                    if (who== 1) {
                        result = "-" + minus(big, b.big);
                    }
                    if (who== 2) {
                        result = "-" + minus(b.big, big);
                    }
                    if (who== 0){
                        result="0";
                    }
                }
            }
        }
            BigInt bb=new BigInt(result);
            return bb;

    }

    private String minus (ArrayList<Integer> bigger,ArrayList<Integer>small){
        String result= "";
        int equal;
        int carry=0;
        int i=bigger.size()-1;
        int j=small.size()-1;
        int dif=i-j;
        for (; dif >= 0; dif--) {
            while (j >= 0) {
                equal=difference(bigger.get(i),small.get(j));
                equal+=carry;
                if (small.get(j)>bigger.get(i)){
                    carry= -1;
                    //equal+=1;
                }else {
                    carry=0;
                }
                result=equal+result;
                j--;
                i--;
            }
            if (dif>0){
                result = (bigger.get(i) + carry) + result;
                if (i==0&&bigger.get(i)==0)return result;
                carry = 0;
                i--;
            }
        }
        if (result.startsWith("0")){
            result=result.substring(1);
        }
        return result;
    }
    @Override
    public boolean equals (Object b){
        if (!(b instanceof Object))return false;
        if (this==b)return true;
        if (this.getClass()!=b.getClass()) return false;
        BigInt bb=(BigInt)b;
        return this.big.equals(bb)&& this.positive==bb.positive;
    }

    private int whoIsBigger (BigInt b){//if the two array are whit the same size
        for (int i=0;i<big.size();i++){
            if (big.get(i)>b.big.get(i))return 1; //first is bigger
            if (big.get(i)<b.big.get(i))return 2; //second is bigger
        }
        return 0; //equal
    }

    public BigInt multiply (BigInt b,int num){ //very slow method!
        int i;
        BigInt bb=new BigInt("0");
        if (num==0){
            return bb;
        }
        bb=b;
        for (i=num-1;i>0;i--){
            bb=bb.plus(b);
        }
        return bb;
    }

    public BigInt multiply (BigInt b){
        ArrayList<BigInt> bigIntList=new ArrayList<BigInt>();
        BigInt bb=new BigInt("0");
        int j=10;
        if(big.get(0)==0||b.big.get(0)==0){
            return bb;
        }
        if (positive&&b.positive){
              bigIntList.add(multiply(b,big.get(big.size()-1)));
              for (int i=big.size()-2;i>=0;i--) {
                  bigIntList.add(multiply(b, big.get(i) * j));
                  j = j * 10;
              }
        }
        if (!positive&&!b.positive){
            positive=true;
            b.positive=true;
            bigIntList.add(multiply(b,big.get(big.size()-1)));
            for (int i=big.size()-2;i>=0;i--) {
                bigIntList.add(multiply(b, big.get(i) * j));
                j = j * 10;
            }
            positive=false;
            b.positive=false;
        }
        if(positive&&!b.positive){
            b.positive=true;
            bigIntList.add(multiply(b,big.get(big.size()-1)));
            for (int i=big.size()-2;i>=0;i--) {
                bigIntList.add(multiply(b, big.get(i) * j));
                j = j * 10;
            }
            b.positive=false;
            bb.positive=false;
        }
        if (!positive&&b.positive){
            positive=true;
            bigIntList.add(multiply(b,big.get(big.size()-1)));
            for (int i=big.size()-2;i>=0;i--) {
                bigIntList.add(multiply(b, big.get(i) * j));
                j = j * 10;
            }
            positive=false;
            bb.positive=false;
        }
        for (int k=bigIntList.size()-1;k>=0;k--) {
            bb = bb.plus(bigIntList.get(k));
        }
        if ((positive&&!b.positive)||(!positive&&b.positive)){
            bb.positive=false;
        }
        return bb;
    }

    public BigInt divide(BigInt b){
        String result="";
        int div=0;
        BigInt bb=b;
        if (big.size()<b.big.size()||(big.size()==b.big.size()&&whoIsBigger(b)==2)){ //less then 1
            result="0";
        }
        if (big.equals(b.big)){ //the two number are equals
            result="1";
        }
        while( bb.big.size()<=big.size()) {
            if (bb.big.size()>=big.size()&&whoIsBigger(bb)==2){
                if (bb.positive&&this.positive) {
                    result = "+" + div;
                    return new BigInt(result);
                }else{
                    result="-"+div;
                    return new BigInt(result);
                }
            }
            bb = bb.plus(b);
            div++;
        }
        result="+"+div;
        return new BigInt(result);
    }

    @Override
    public String toString (){
        String s="";
        int num;
        for (int i=this.big.size()-1;i>=0;i--){
            num=this.big.get(i);
            s=num+s;
        }
        if (this.positive){
            s="+"+s;
        }else {
            s="-"+s;
        }
        return s;
    }

    @Override
    public int compareTo(BigInt b) {


        if (this.positive && (!b.positive)) {
            return 1;
        }
        if ((!this.positive) && b.positive) {
            return -1;
        }
        if (this.positive && b.positive) {
            if (this.big.size()>b.big.size()){
                return 1;
            }
            if (this.big.size()<b.big.size()){
                return -1;
            }
            if (whoIsBigger(b) == 1) {
                return 1;
            }
            if (whoIsBigger(b) == 2) {
                return -1;
            }
            return 0;
        }
        if (!this.positive&&!b.positive) {
            if (this.big.size()>b.big.size()){
                return -1;
            }
            if (this.big.size()<b.big.size()) {
                return 1;
            }
            if (whoIsBigger(b) == 1) {
                return -1;
            }
            if (whoIsBigger(b) == 2) {
                return 1;
            }
        }
        return 0;
    }

}




























