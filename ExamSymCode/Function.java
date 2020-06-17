/*
 * XXX XXXXXXXXXXXXX
 * 一个基于JAVA的考试及评卷系统
 * 系统: windows 10
 * IDE: Eclipse
 * JRE: jre 1.8.0_251
 * 电子科技大学2020年春JAVA语言程序设计
 * 本程序为具体实现的主程序
 * 本系统实现功能和运行方法请看readme.md
 * Date: Wed Jun 17 10:27:35 GMT+08:00 2020
 */

import javax.imageio.ImageIO;//声明导入的类
import java.util.Random;
import java.lang.Math;
import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

class Function implements ActionListener
{
	/* 
	 * 创建窗口、标签
	 */
	Date now=new Date();
	JButton b1;
    JLabel l1;
	JFrame f = new JFrame("考试评卷系统");
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel p4 = new JPanel();

	// 题号和总分
	int index = 0,total = 0;
	
	// 15位数组存储题目编号
	int[] QueList = new int[15];
	
	// 设置文件具体路径以读出写入
	File file = new File("D:\\Desktop\\试题库2.txt");
	//File file = new File("D:\\Desktop\\试题库2.txt");//换个试题源
	File ans = new File("D:\\Desktop\\答题数据.txt");
	QuestionBank questions[] = new QuestionBank[100];

	JLabel questionString = new JLabel("题目：");
	JLabel choiceString = new JLabel("选项：");
	JLabel totalString = new JLabel("总分：");
	TextArea question = new TextArea("",2,30,2);
    JTextField totaltf = new JTextField("0",10);
	JRadioButton choiceA = new JRadioButton("A");
	JRadioButton choiceB = new JRadioButton("B");
	JRadioButton choiceC = new JRadioButton("C");
	JRadioButton choiceD = new JRadioButton("D");
	ButtonGroup radioGroup = new ButtonGroup();
	
	//向Button里添加文字
	JButton beginbt = new JButton("答题开始");
	JButton nextbt = new JButton("下个题目");
	JButton practiceAgainbt = new JButton("重新开始");

	Function()
	{
		/* 
		 * 系统布局
		 */
		f.setSize(400,400);
		f.setLocation(50,50);
		f.setVisible(true);
		
		f.setLayout(new GridLayout(4,1,10,10));
		p1.setLayout(new FlowLayout(FlowLayout.CENTER));
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));
		p4.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		radioGroup.add(choiceA);
		radioGroup.add(choiceB);
		radioGroup.add(choiceC);
		radioGroup.add(choiceD);
		
		// 添加问题
		p1.add(questionString);
		p1.add(question);
		
		// 添加选项
		p2.add(choiceString);
		p2.add(choiceA);
		p2.add(choiceB);
		p2.add(choiceC);
		p2.add(choiceD);
		
		// 添加总分
		p3.add(totalString);
		p3.add(totaltf);
		
		// 添加三个按钮
		p4.add(beginbt);
		p4.add(nextbt);
		p4.add(practiceAgainbt);
			
		f.add(p1);
		f.add(p2);
		f.add(p3);
		f.add(p4);
		
		nextbt.addActionListener(this);
		beginbt.addActionListener(this);
		practiceAgainbt.addActionListener(this);
				
		CreatNum();
		readFile();
		
		// 使界面为系统风格
		try 
		{ 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //系统风格
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");//Nimbus风格
		}
		catch (Exception e)
		{ 
			e.printStackTrace();
		}	
	}
	
	/* 
	 * 产生随机题号并将它们从大到小排序
	 */
	void CreatNum(){
		for (int i = 0;i<15;i++){
			int j = (int)(Math.random()*50);
			QueList[i] = j;
		}
		Arrays.sort(QueList);
	}
	
	/*
	 * 从指定文件中读取题目选项和答案
	 */
	void readFile()
	{
		String s,question,choiceA,choiceB,choiceC,choiceD,answer;
		try
		{
			int count=0;
			FileInputStream in=new FileInputStream(file);//打开题目文件
			byte b[]=new byte[in.available()];
			in.read(b);
			s=new String(b);
			
			// 分割题目和选项
			String c[]=s.split("&&");
			
			for(int i=0;i<c.length;i++)
			{
				// 分配题目选项和答案
				question = c[i++];
				choiceA = c[i++];
				choiceB = c[i++];
				choiceC = c[i++];
				choiceD = c[i++];
				answer = c[i];
				questions[count++] = new QuestionBank(question,choiceA,choiceB,choiceC,choiceD,answer);
			}
		}catch(Exception e1){ e1.printStackTrace();}
	}
	
	/*
	 * 判断回答是否正确并做出回应，总分累加
	 */
	void ifRight()
	{
		// 答案取自题目文件的第五位
		// 若正确总分total加一
		if(choiceA.isSelected())
		{
			if(questions[(int)QueList[index-1]].answer.equals(choiceA.getText()))//
			{
				total+=1;
			}
			// 将答题的数据写入指定的文件保存
			try {
				FileWriter out = new FileWriter(ans, true);
				out.write("["+(1+QueList[(index-1)]));
				out.write("、A]   ");
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(choiceB.isSelected())
		{
			if(questions[QueList[index-1]].answer.equals(choiceB.getText()))
			{
				total+=1;
			}
			// 将答题的数据写入指定的文件保存
			try {
				FileWriter out = new FileWriter(ans, true);
				out.write("["+(QueList[(index-1)]+1));
				out.write("、B]   ");
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(choiceC.isSelected())
		{
			if(questions[QueList[index-1]].answer.equals(choiceC.getText()))
			{
				total+=1;
			}
			// 将答题的数据写入指定的文件保存
			try {
				FileWriter out = new FileWriter(ans, true);
				out.write("["+(1+QueList[(index-1)]));
				out.write("、C]   ");
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(choiceD.isSelected())
		{
			if(questions[QueList[index-1]].answer.equals(choiceD.getText()))
			{
				total+=1;
			}
			// 将答题的数据写入指定的文件保存
			try {
				FileWriter out = new FileWriter(ans, true);
				out.write("["+(1+QueList[(index-1)]));
				out.write("、D]   ");
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 在判断完这一选项是否正确后设置总分
		totaltf.setText(Integer.toString(total));
	}
	
	/*
	 * 针对输入的参数polar，将题目、选项设置为第polar位
	 */
	void setQuestion(int polar)
	{
		// 设置题目选项
		question.setText(questions[polar].question);
		choiceA.setText(questions[polar].choiceA);
		choiceB.setText(questions[polar].choiceB);
		choiceC.setText(questions[polar].choiceC);
		choiceD.setText(questions[polar].choiceD);
		radioGroup.clearSelection();
	}
	
	/*
	 * 按下各个按钮后的结果
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		// 按下“下个题目”，index加一，跳转到下一题
		if(e.getSource() == nextbt)
		{
			ifRight();
			if(index<11)
			{
				setQuestion((int)QueList[index]);
				index += 1;
			}
			
		}
		
		// 按下“开始答题”，在答题数据文件中写入抬头
		if(e.getSource() == beginbt)
		{
			try {
				FileWriter out = new FileWriter(ans, true);
				out.write(
				   "\n\n =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n"+
				   "                                                                        \n"+
				   "                       "          +now.toString()+        "                 \n"+
				   "      ,                                                                \n"+
				   "     //\\^/`\\                                                           \n"+
				   "    | \\/    |                                                           \n"+
				   "    | |     |             "+"电子科技大学JAVA语言程序设计"+"                   \n"+
				   "     \\ \\    /                                                      _ _       \n"+
				   "      '\\\\//'                                                     _{ ' }_     \n"+
				   "        ||                      XXX XXXXXXXXXXXXX              { `.!.` }    \n"+
				   "        ||                      <XXXXXXXXX@qq.com>               ',_/Y\\_,'    \n"+
				   "        ||  ,                                                     {_,_}      \n"+
				   "    |\\  ||  |\\                                                      |        \n"+
				   "    | | ||  | |                     MY BLOG:                      (\\|  /)    \n"+
				   "    | | || / /            <https://uestcjhx.github.io/>            \\| //     \n"+
				   "     \\ \\||/ /                                                       |//      \n"+
				   "      `\\\\//`   \\\\   \\./    \\\\ /     //    \\\\./         \\\\   //   \\\\ |/ /     \n"+
				   " ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ \n"
						  +"\n");
				out.flush();
				out.close();
			} catch (IOException x) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		
		// 按下“重新开始”，则返回第一题，分数变为0
		if(e.getSource() == practiceAgainbt)
		{
			index = 0;
			total = 0;//分数为0
			totaltf.setText(Integer.toString(total));
			try {
				FileWriter out = new FileWriter(ans, true);
				out.write("\n\n");
				out.flush();
				out.close();
			} catch (IOException x) {
				// TODO Auto-generated catch block
				// x.printStackTrace();
			}
		}
	}
}