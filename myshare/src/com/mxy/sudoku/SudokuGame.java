package com.mxy.sudoku;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.skin.BusinessBlackSteelSkin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Created by mxy} on 2017/12/3.
 */
public class SudokuGame extends JFrame{
    private static JTextField a[][] = new JTextField[9][9];//存储文本框中的数字
    private static int[][] ans = new int [9][9];//输入答案后存储的数组
    Sudoku sudoku = new Sudoku();
    public int[][] m =  sudoku.generateSudoku();
    public int[][] n ;


    //挖洞
    private int[][] change(int a[][]){
        Random r = new Random();
        int a1;
        int a2;
        for(int i = 0 ; i < 3 ; i++){
            a1 = r.nextInt(9);
            a2 = r.nextInt(9);
            a[a1][a2] = 0;
        }
        return a;
    }

    //界面布局设置,容器->组件->布局
    public SudokuGame() {
        setLayout(new BorderLayout(2,1));//边界布局管理器
        JMenuItem jmiOk = new JMenuItem("提交");
        JMenuItem jmiExplain = new JMenuItem("详情");
        JMenuItem jmiMessage = new JMenuItem("信息");
        JPanel p1 = new JPanel();//flowlayout 流式布局管理器
        p1.add(jmiOk);
        p1.add(jmiExplain);
        p1.add(jmiMessage);
        JPanel p2 = new JPanel(new GridLayout(9,9,5,5));
        add(p1,BorderLayout.NORTH);
        add(p2,BorderLayout.CENTER);
        n = change(m);
        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                if(n[i][j] != 0){
                    a[i][j] = new JTextField(""+n[i][j]);
                    a[i][j].setHorizontalAlignment(JTextField.CENTER);
                    a[i][j].setEditable(false);
                    p2.add(a[i][j]);
                }else{
                    a[i][j] = new JTextField();
                    a[i][j].setHorizontalAlignment(JTextField.CENTER);
                    p2.add(a[i][j]);
                }
            }
        }
        //事件监听 implements ActionLisrener
        jmiOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gettext() == 1){
                    if(judge()){
                        JOptionPane.showMessageDialog(null,"your answer is right!","Result",JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(null,"your answer is wrong!","Result",JOptionPane.INFORMATION_MESSAGE);

                    }
                }
            }
        });
        Explain explain = new Explain();
        jmiExplain.addActionListener(explain);
        Message message = new Message();
        jmiMessage.addActionListener(message);

    }

    //获取文本框中的内容
    static int gettext(){
        for(int i = 0 ; i < 9 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                try {
                    ans[i][j] = Integer.parseInt(a[i][j].getText());
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"数字中包含非数字，请重新输入");
                    return 0;
                }
            }
        }
        return 1;
    }

    //判断填入的答案是否正确
    public static boolean judge(){
        int[][] answer = ans;
        //行判断
        for(int i = 0 ; i < 9 ; i ++ ){
            int s[] = new int[9];
            for(int j = 0 ; j  <9 ; j ++){
                s[j] = answer[i][j];
            }
            if(isRepeated(s) == false){
                return false;
            }
        }
        //列判断
        for(int i = 0 ; i < 9 ; i ++ ){
            int s[] = new int[9];
            for(int j = 0 ; j  <9 ; j ++){
                s[j] = answer[j][i];
            }
            if(isRepeated(s) == false){
                return false;
            }
        }
        //九宫格判断
        for(int i = 0 ; i < 3 ; i ++){
            for(int j = 0 ; j < 3 ; j ++){
                int s[] = new int[9];
                int k = 0;
                for(int m = i * 3 ; m < i * 3 +3 ; m ++){
                    for(int n = j * 3 ; n < j *3 +3 ; n++){
                        s[k] = answer[m][n];
                        k ++;
                    }
                }
                if(isRepeated(s) == false){
                    return false;
                }
            }
        }
        return true;
    }

    //判断数组中是否有重复数字
    public static boolean isRepeated(int[] a){
        for(int i = 0 ; i < 9 ; i ++){
            for(int j = 0 ; j < 9 ; j++){
                if( i == j)
                    continue;
                if(a[i] == a[j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        SudokuGame frame = new SudokuGame();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SubstanceLookAndFeel.setSkin(new BusinessBlackSteelSkin());
                try {

                    frame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            int flag = JOptionPane.showConfirmDialog(frame,
                                    "Sure to close?", "Care!",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE);
                            if (JOptionPane.YES_OPTION == flag) {
                                System.exit(0);
                            } else {
                                return;
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        frame.setTitle("SuDoku");
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

}
class  Explain implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,"填入数字保证每行每列及每个小九宫格内数字无重复","Explain",JOptionPane.INFORMATION_MESSAGE);
    }
}
class  Message implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,"made by Mr.Mao","Message",JOptionPane.INFORMATION_MESSAGE);
    }
}
