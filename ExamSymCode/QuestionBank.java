/*
 * XXX XXXXXXXXXXXXX
 * 一个基于JAVA的考试及评卷系统
 * 系统: windows 10
 * IDE: Eclipse
 * JRE: jre 1.8.0_251
 * 电子科技大学2020年春JAVA语言程序设计
 * 本程序用于储存题目、选项、答案的类
 * 本系统实现功能和运行方法请看readme.md
 * Date: Wed Jun 17 10:27:35 GMT+08:00 2020
 */

class QuestionBank {
	String question;
	String choiceA, choiceB, choiceC, choiceD;
	String answer;

	QuestionBank(String question, String choiceA, String choiceB,
			String choiceC, String choiceD, String answer) {
		this.question = question;
		this.choiceA = choiceA;
		this.choiceB = choiceB;
		this.choiceC = choiceC;
		this.choiceD = choiceD;
		this.answer = answer;
	}
}