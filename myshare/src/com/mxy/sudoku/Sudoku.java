package com.mxy.sudoku;

import java.util.Random;

/**
 * Created by mxy} on 2017/12/2.
 */
public class Sudoku {
    private static int[][] n = new int[9][9];
    private static int[] num = {1,2,3,4,5,6,7,8,9};
    //生成数独
    public static int[][] generateSudoku(){
        for(int i = 0 ; i < 9 ; i ++){
            int time = 0;
            for(int j = 0 ; j < 9 ; j++){
                n[i][j] = generateNum(time);
                if(n[i][j] == 0){
                    if(j > 0){
                        j-=2;
                        continue;
                    }else{
                        i--;
                        j = 8;
                        continue;
                    }
                }
                if(checkAll(i,j)){
                    time = 0;
                }else{
                    time ++;
                    j--;
                }

            }
        }
        return n;
    }
    private static boolean checkAll(int row,int col){
        return (checkRow(row)&checkCol(col)&checkNine(row,col));
    }

    /**
     * 检查行是否符合要求
     *
     * @param row  检查的行号
     * @return true代表符合要求
     */
    private static boolean checkRow(int row){
        for(int i = 0 ; i < 8 ; i ++){
            if(n[row][i] == 0){
                continue;
            }
            for (int j = i + 1 ; j < 9 ; j++){
                if(n[row][i] == n[row][j]){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 检查列是否符合要求
     *
     * @param col 检查的列号
     * @return true代表符合要求
     */
    private static boolean checkCol(int col){
        for(int i = 0 ; i < 8 ; i ++){
            if(n[i][col] == 0){
                continue;
            }
            for (int j = i + 1 ; j < 9 ; j++){
                if(n[i][col] == n[j][col]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检查每个九宫格是否符合要求
     * @param row 检查的行号
     * @param col 检查的列号
     * @return true代表符合要求
     */
    private static boolean checkNine(int row,int col){
        int m = row / 3 * 3;//所在九宫格的左上角行坐标
        int k = col / 3 * 3;//所在九宫格的左上角列坐标
        for(int i = 0 ; i < 8 ; i++){
            if(n[m + i / 3][k + i % 3] == 0){
                continue;
            }
            for (int j = i + 1 ; j < 9 ; j ++){
                if(n[m + i / 3][k + i % 3] == n[m + j / 3][k + j % 3]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 产生1-9之间的随机数字 规则：生成的随机数字放置在数组8-time下标的位置，随着time的增加，已经尝试过的数字将不会在取到
     * 说明：即第一次次是从所有数字中随机，第二次时从前八个数字中随机，依次类推， 这样既保证随机，也不会再重复取已经不符合要求的数字，提高程序的效率
     * @param time 填充的次数，0代表第一次填充
     * @return
     */
    //123456789
    //19345678 2
    //1984567 32
    //798456 132
    private static Random r = new Random();
    private static int generateNum(int time){
        //初始化数字源
        if(time == 0){
            for(int i = 0 ; i < 9 ; i++){
                num[i] = i + 1;
            }
        }
        //代表卡住 由主程序处理
        if(time == 9){
            return 0;
        }
        int ranNum = r.nextInt(9-time);
        int temp = num[8-time];
        num[8-time] = num[ranNum];
        num[ranNum] = temp;
        return num[8-time];
    }

    //主函数
    public static void main(String[] args) {
        int[][] sudoku = generateSudoku();
        // 输出结果
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(sudoku[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
