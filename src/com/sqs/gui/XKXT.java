package com.sqs.gui;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Created by SQS19911128 on 2021/1/25.
 */
public class XKXT {
    public static void main(String[]args){

        MUSIC m=new MUSIC();
        DLJM dl=new DLJM("登陆学生选课系统");
        dl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dl.setLocation(400, 200);
        dl.setSize(500,300);
        dl.setVisible(true);
    }
}
class MUSIC{
    public MUSIC(){
        try{
            URL u1;
            URI u2;
            File f=new File("杨幂-爱的供养.wav");
            u2=f.toURI();
            u1=u2.toURL();
            AudioClip a=Applet.newAudioClip(u1);
            a.loop();
        }catch(MalformedURLException e){}
    }
}
class DLJM extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    Choice c;
    JLabel l1,l2,l3,l;
    JTextField t1;
    JPasswordField t2;
    JButton b1,b2;
    public DLJM(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("http_imgload[6].jpg");
        l=new JLabel(i);
        l.setBounds(0,0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,30));
        c=new Choice();
        c.add("      管  理  员      ");
        c.add("      教      师      ");
        c.add("      学      生      ");
        l1=new JLabel("登陆身份");
        l2=new JLabel("用户账号");
        l3=new JLabel("用户密码");
        t1=new JTextField("",10);
        t2=new JPasswordField("",10);
        b1=new JButton("确定");
        b2=new JButton("退出");
        t2.addActionListener(
                new ActionListener( ) {
                    public void actionPerformed( ActionEvent e ) {
                        Object pwd = ( ( ( JPasswordField ) e.getSource( ) ).getPassword( ) );
                        JOptionPane.showMessageDialog( null, "Your password is " + pwd );
                    }
                }
        );
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(c);
        p.add(l2);
        p.add(t1);
        p.add(l3);
        p.add(t2);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){

            if((t1.getText().equals("")||t2.getText().equals(""))||
                    ((!c.getSelectedItem().equals("      管  理  员      ")||!t1.getText().equals("SQS")||!t2.getText().equals("200628"))&&
                            (!c.getSelectedItem().equals("      教      师      ")||!t1.getText().equals("JS")||!t2.getText().equals("123456"))&&
                            (!c.getSelectedItem().equals("      学      生      ")||!t1.getText().equals("XS")||!t2.getText().equals("123456"))))
            {   t1.setText("");t2.setText("");
                JOptionPane.showMessageDialog(this,"账号或密码错误!","提示",JOptionPane.WARNING_MESSAGE);}
            if(c.getSelectedItem().equals("      管  理  员      ")&&t1.getText().equals("SQS")&&t2.getText().equals("200628")){
                this.dispose();
                GLY gly=new GLY("管理员界面");
                gly.setLocation(400, 200);
                gly.setSize(500,300);
                gly.setVisible(true);
            }
            if(c.getSelectedItem().equals("      教      师      ")&&t1.getText().equals("JS")&&t2.getText().equals("123456")){
                this.dispose();
                JS js=new JS("教师界面");
                js.setLocation(400, 200);
                js.setSize(500,300);
                js.setVisible(true);
            }
            if(c.getSelectedItem().equals("      学      生      ")&&t1.getText().equals("XS")&&t2.getText().equals("123456")){
                this.dispose();
                XS xs=new XS("学生界面");
                xs.setLocation(400, 200);
                xs.setSize(500,300);
                xs.setVisible(true);
            }
        }
        if(e.getSource()==b2){
            System.exit(0);
        }
    }
}
class GLY extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l;
    JButton b1,b2,b3,b4;
    public GLY(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("http_imgload[5].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        b1=new JButton("管理教师");
        b2=new JButton("管理学生");
        b3=new JButton("管理课程");
        b4=new JButton("返回上级");
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            GLJSJM gly=new GLJSJM("管理教师界面");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
        if(e.getSource()==b2){
            this.dispose();
            GLXSJM js=new GLXSJM("管理学生界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b3){
            this.dispose();
            GLKCJM xs=new GLKCJM("管理课程界面");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);
        }
        if(e.getSource()==b4){
            this.dispose();
            DLJM dl=new DLJM("登陆学生选课系统");
            dl.setLocation(400, 200);
            dl.setSize(500,300);
            dl.setVisible(true);
        }
    }
}
class JS extends JFrame implements ActionListener{
    JPanel p;
    JButton b1,b2,b3,b4,b5;
    ImageIcon i;
    JLabel l;
    public JS(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("111115B19-1[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,50));
        b1=new JButton("教师代课");
        b2=new JButton("教师信息");
        b3=new JButton("学生成绩");
        b4=new JButton("课程信息");
        b5=new JButton("返回上级");
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.add(b5);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            JSDK gly=new JSDK("教师个人代课");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
        if(e.getSource()==b2){
            this.dispose();
            DLJSXX js=new DLJSXX("教师个人信息");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b3){
            this.dispose();
            XSCJ js=new XSCJ("管理学生成绩");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b4){
            this.dispose();
            CXKC2 js=new CXKC2("查询课程信息");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b5){
            this.dispose();
            DLJM dl=new DLJM("登陆学生选课系统");
            dl.setLocation(400, 200);
            dl.setSize(500,300);
            dl.setVisible(true);
        }
    }

}
class XS extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l;
    JButton b1,b2,b3,b4;
    public XS(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("psbe[10].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        b1=new JButton("学生选课");
        b2=new JButton("学生信息");
        b3=new JButton("课程信息");
        b4=new JButton("返回上级");
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            XSXK1 gly=new XSXK1("学生个人选课");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
        if(e.getSource()==b2){
            this.dispose();
            DLXSXX js=new DLXSXX("学生个人信息");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b3){
            this.dispose();
            CXKC3 js=new CXKC3("查询课程信息");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b4){
            this.dispose();
            DLJM dl=new DLJM("登陆学生选课系统");
            dl.setLocation(400, 200);
            dl.setSize(500,300);
            dl.setVisible(true);
        }
    }

}
class GLJSJM extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l;
    JButton b1,b2,b3,b4,b5;
    public GLJSJM(String str){
        super(str);
        JPanel p=new JPanel();
        i=new ImageIcon("qingchun09.jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,50));
        b1=new JButton("添加教师");
        b2=new JButton("删除教师");
        b3=new JButton("查询教师");
        b4=new JButton("教师工作");
        b5=new JButton("返回上级");
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        p.add(b5);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            TJJS gly=new TJJS("添加教师信息");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
        if(e.getSource()==b2){
            this.dispose();
            SCJS js=new SCJS("删除教师信息");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b3){
            this.dispose();
            CXJS xs=new CXJS("查询教师信息");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);
        }
        if(e.getSource()==b4){
            this.dispose();
            JSGZ xs=new JSGZ("管理教师工作");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);
        }
        if(e.getSource()==b5){
            this.dispose();
            GLY gly=new GLY("管理员界面");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
    }
}
class GLXSJM extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l;
    JButton b1,b2,b3,b4;
    public GLXSJM(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("截图1323070283.jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        b1=new JButton("添加学生");
        b2=new JButton("删除学生");
        b3=new JButton("查询学生");
        b4=new JButton("返回上级");
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            TJXS gly=new TJXS("添加学生信息");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
        if(e.getSource()==b2){
            this.dispose();
            SCXS js=new SCXS("删除学生信息");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b3){
            this.dispose();
            CXXS xs=new CXXS("查询学生信息");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);
        }
        if(e.getSource()==b4){
            this.dispose();
            GLY gly=new GLY("管理员界面");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
    }
}
class GLKCJM extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l;
    JButton b1,b2,b3,b4;
    public GLKCJM(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("70b90ef82ea9c0dc58ee905c[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        b1=new JButton("添加课程");
        b2=new JButton("删除课程");
        b3=new JButton("查询课程");
        b4=new JButton("返回上级");
        p.add(b1);
        p.add(b2);
        p.add(b3);
        p.add(b4);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            TJKC gly=new TJKC("添加课程信息");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
        if(e.getSource()==b2){
            this.dispose();
            SCKC js=new SCKC("删除课程信息");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
        if(e.getSource()==b3){
            this.dispose();
            CXKC1 xs=new CXKC1("查询课程信息");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);
        }
        if(e.getSource()==b4){
            this.dispose();
            GLY gly=new GLY("管理员界面");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
    }
}
class TJJS extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l2,l3,l4,l5,l;
    JTextField t1,t2,t3,t4,t5;
    JButton b1,b2;
    public TJJS(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("4e7a010a4fa3f97be824885b[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,15));
        l1=new JLabel("教职工号");
        l2=new JLabel("教师姓名");
        l3=new JLabel("教师性别");
        l4=new JLabel("教师年龄");
        l5=new JLabel("教师职称");
        t1=new JTextField("",10);
        t2=new JTextField("",10);
        t3=new JTextField("",10);
        t4=new JTextField("",10);
        t5=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(l3);
        p.add(t3);
        p.add(l4);
        p.add(t4);
        p.add(l5);
        p.add(t5);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")){
                JOptionPane.showMessageDialog(this,"教职工号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="INSERT INTO 教师表(教职工号,姓名,性别,年龄,职称) VALUES('"+t1.getText()+"','"+t2.getText()+"','"+t3.getText()+"','"+t4.getText()+"','"+t5.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");t2.setText(""); t3.setText("");t4.setText(""); t5.setText("");
                JOptionPane.showMessageDialog(this,"添加教师成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            GLJSJM gly=new GLJSJM("管理教师界面");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
    }
}
class SCJS extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l;
    JTextField t1;;
    JButton b1,b2;
    public SCJS(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("psue[21].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        l1=new JLabel("教职工号");
        t1=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")){JOptionPane.showMessageDialog(this,"教职工号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="DELETE FROM 教师表 WHERE(教职工号='"+t1.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");
                JOptionPane.showMessageDialog(this,"删除教师成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            GLJSJM gly=new GLJSJM("管理教师界面");
            gly.setLocation(400, 200);
            gly.setSize(500,300);
            gly.setVisible(true);
        }
    }

}
class CXJS extends JFrame implements ActionListener{
    JPanel p;
    JButton b1;
    MyTableModel11 mt;
    JTable t;
    JScrollPane s;
    public CXJS(String str){
        super(str);
        setLayout(new BorderLayout());
        mt=new MyTableModel11();
        t=new JTable(mt);
        s=new JScrollPane(t);
        p=new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,10));
        b1=new JButton("返回上级");
        b1.addActionListener(this);
        p.add(b1);
        add(p,"South");
        add(s,"Center");
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            GLJSJM js=new GLJSJM("管理教师界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }

}
class MyTableModel11 extends AbstractTableModel{

    String jsnum,jsname,sex,age,work;
    final String[] columnNames={"教职工号","姓名","性别","年龄","职称"};
    Object[][]data=new Object[100][5];
    public MyTableModel11(){
        int m=0;
        String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
        Connection con;
        String sql;
        Statement stmt;
        try{
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        }catch(java.lang.ClassNotFoundException e){
        }
        try{
            con=DriverManager.getConnection(url,"SQS","200628");
            stmt=con.createStatement();
            sql=sql="SELECT * FROM 教师表";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                jsnum=rs.getString(1);
                jsname=rs.getString(2);
                sex=rs.getString(3);
                age=rs.getString(4);
                work=rs.getString(5);
                data[m][0]=jsnum;data[m][1]=jsname;data[m][2]=sex;data[m][3]=age;data[m++][4]=work;
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
        }
    }
    public int getColumnCount(){
        return columnNames.length;
    }
    public int getRowCount(){
        return data.length;
    }
    public String getColumnName(int col){
        return columnNames[col];
    }
    public Object getValueAt(int row,int col){
        return data[row][col];
    }
    public void setValueAt(Object value,int row,int col){

        System.out.println("New value of data:");
        int numRows=getRowCount();
        int numCols=getColumnCount();
        for(int i=0;i<numRows;i++){
            System.out.print("row"+i+":");
            for(int j=0;j<numCols;j++){
                System.out.print(" "+data[i][j]);
            }   System.out.println();
        }System.out.println("----------------------");
    }
}
class JSGZ extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l2,l3,l;
    JTextField t1,t2,t3;
    JButton b1,b2;
    public JSGZ(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("http_imgload[8].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,30));
        l1=new JLabel("教职工号");
        l2=new JLabel("课  程  号");
        l3=new JLabel("代课效果");
        t1=new JTextField("",10);
        t2=new JTextField("",10);
        t3=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(l3);
        p.add(t3);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")||t2.getText().equals("")||t3.getText().equals("")){JOptionPane.showMessageDialog(this,"教职工号.课程号.代课效果不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="UPDATE 代课表 SET 效果='"+t3.getText()+"' WHERE(教职工号='"+t1.getText()+"' AND 课程号='"+t2.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");t2.setText(""); t3.setText("");
                JOptionPane.showMessageDialog(this,"管理教师工作成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            GLJSJM js=new GLJSJM("管理教师界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class TJXS extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l2,l3,l4,l5,l;
    JTextField t1,t2,t3,t4,t5;
    JButton b1,b2;
    public TJXS(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("http_imgload[8].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,15));
        l1=new JLabel("学生学号");
        l2=new JLabel("学生姓名");
        l3=new JLabel("学生性别");
        l4=new JLabel("学生年龄");
        l5=new JLabel("学生专业");
        t1=new JTextField("",10);
        t2=new JTextField("",10);
        t3=new JTextField("",10);
        t4=new JTextField("",10);
        t5=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(l3);
        p.add(t3);
        p.add(l4);
        p.add(t4);
        p.add(l5);
        p.add(t5);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")){JOptionPane.showMessageDialog(this,"学生学号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="INSERT INTO 学生表(学号,姓名,性别,年龄,专业) VALUES('"+t1.getText()+"','"+t2.getText()+"','"+t3.getText()+"','"+t4.getText()+"','"+t5.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");t2.setText(""); t3.setText(""); t4.setText("");t5.setText("");
                JOptionPane.showMessageDialog(this,"添加学生成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            GLXSJM js=new GLXSJM("管理学生界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class SCXS extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l;
    JTextField t1;;
    JButton b1,b2;
    public SCXS(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("f679278ccfd256a4f11f361f[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        l1=new JLabel("学生学号");
        t1=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")){JOptionPane.showMessageDialog(this,"学生学号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="DELETE FROM 学生表 WHERE(学号='"+t1.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");
                JOptionPane.showMessageDialog(this,"删除学生成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            GLXSJM js=new GLXSJM("管理学生界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class CXXS extends JFrame implements ActionListener{
    JPanel p;
    JButton b1;
    MyTableModel12 mt;
    JTable t;
    JScrollPane s;
    public CXXS(String str){
        super(str);
        setLayout(new BorderLayout());
        mt=new MyTableModel12();
        t=new JTable(mt);
        s=new JScrollPane(t);
        p=new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,10));
        b1=new JButton("返回上级");
        b1.addActionListener(this);
        p.add(b1);
        add(p,"South");
        add(s,"Center");
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            GLXSJM js=new GLXSJM("管理学生界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class MyTableModel12 extends AbstractTableModel{
    String xsnum,xsname,sex,age,profess;
    final String[] columnNames={"学号","姓名","性别","年龄","专业"};
    Object[][]data=new Object[100][5];
    public MyTableModel12(){
        int m=0;
        String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
        Connection con;
        String sql;
        Statement stmt;
        try{
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        }catch(java.lang.ClassNotFoundException e){
        }
        try{
            con=DriverManager.getConnection(url,"SQS","200628");
            stmt=con.createStatement();
            sql=sql="SELECT * FROM 学生表";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                xsnum=rs.getString(1);
                xsname=rs.getString(2);
                sex=rs.getString(3);
                age=rs.getString(4);
                profess=rs.getString(5);
                data[m][0]=xsnum;data[m][1]=xsname;data[m][2]=sex;data[m][3]=age;data[m++][4]=profess;
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
        }
    }
    public int getColumnCount(){
        return columnNames.length;
    }
    public int getRowCount(){
        return data.length;
    }
    public String getColumnName(int col){
        return columnNames[col];
    }
    public Object getValueAt(int row,int col){
        return data[row][col];
    }
    public void setValueAt(Object value,int row,int col){
        int numRows=getRowCount();
        int numCols=getColumnCount();
        for(int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                System.out.print(" "+data[i][j]);
            }
        }
    }
}

class TJKC extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l2,l3,l4,l5,l;
    JTextField t1,t2,t3,t4,t5;
    JButton b1,b2;
    public TJKC(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("http_imgload[9].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,15));
        l1=new JLabel("课  程  号");
        l2=new JLabel("课  程  名");
        l3=new JLabel("学        分");
        l4=new JLabel("上课时间");
        l5=new JLabel("上课地点");
        t1=new JTextField("",10);
        t2=new JTextField("",10);
        t3=new JTextField("",10);
        t4=new JTextField("",10);
        t5=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(l3);
        p.add(t3);
        p.add(l4);
        p.add(t4);
        p.add(l5);
        p.add(t5);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")){JOptionPane.showMessageDialog(this,"课程号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="INSERT INTO 课程表 VALUES('"+t1.getText()+"','"+t2.getText()+"','"+t3.getText()+"','"+t4.getText()+"','"+t5.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");t2.setText(""); t3.setText(""); t4.setText(""); t5.setText("");
                JOptionPane.showMessageDialog(this,"添加课程成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            GLKCJM xs=new GLKCJM("管理课程界面");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);
        }
    }
}

class SCKC extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l;
    JTextField t1;;
    JButton b1,b2;
    public SCKC(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("d198149725bbbf54d31b700a[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        l1=new JLabel("该课程号");
        t1=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")){JOptionPane.showMessageDialog(this,"课程号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="DELETE FROM 课程表 WHERE(课程号='"+t1.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");
                JOptionPane.showMessageDialog(this,"删除课程成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            GLKCJM xs=new GLKCJM("管理课程界面");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);;
        }
    }
}
class CXKC1 extends JFrame implements ActionListener{
    JPanel p;
    JButton b1;
    MyTableModel13 mt;
    JTable t;
    JScrollPane s;
    public CXKC1(String str){
        super(str);
        setLayout(new BorderLayout());
        mt=new MyTableModel13();
        t=new JTable(mt);
        s=new JScrollPane(t);
        p=new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,10));
        b1=new JButton("返回上级");
        b1.addActionListener(this);
        p.add(b1);
        add(p,"South");
        add(s,"Center");
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            GLKCJM js=new GLKCJM("管理课程界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class CXKC2 extends JFrame implements ActionListener{
    JPanel p;
    JButton b1;
    MyTableModel13 mt;
    JTable t;
    JScrollPane s;
    public CXKC2(String str){
        super(str);
        setLayout(new BorderLayout());
        mt=new MyTableModel13();
        t=new JTable(mt);
        s=new JScrollPane(t);
        p=new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,10));
        b1=new JButton("返回上级");
        b1.addActionListener(this);
        p.add(b1);
        add(p,"South");
        add(s,"Center");
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            JS js=new JS("教师界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class CXKC3 extends JFrame implements ActionListener{
    JPanel p;
    JButton b1;
    MyTableModel13 mt;
    JTable t;
    JScrollPane s;
    public CXKC3(String str){
        super(str);
        setLayout(new BorderLayout());
        mt=new MyTableModel13();
        t=new JTable(mt);
        s=new JScrollPane(t);
        p=new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,10));
        b1=new JButton("返回上级");
        b1.addActionListener(this);
        p.add(b1);
        add(p,"South");
        add(s,"Center");
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            XS js=new XS("学生界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class MyTableModel13 extends AbstractTableModel{
    String kcnum,kcname,score,kctime,kcaddress;
    final String[] columnNames={"课程号","课程名","学分","上课时间","上课地点"};
    Object[][]data=new Object[100][5];
    public  MyTableModel13(){
        int m=0;
        String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
        Connection con;
        String sql;
        Statement stmt;
        try{
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        }catch(java.lang.ClassNotFoundException e){
        }
        try{
            con=DriverManager.getConnection(url,"SQS","200628");
            stmt=con.createStatement();
            sql=sql="SELECT * FROM 课程表";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                kcnum=rs.getString(1);
                kcname=rs.getString(2);
                score=rs.getString(3);
                kctime=rs.getString(4);
                kcaddress=rs.getString(5);
                data[m][0]=kcnum;data[m][1]=kcname;data[m][2]=score;data[m][3]=kctime;data[m++][4]=kcaddress;
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
        }
    }
    public int getColumnCount(){
        return columnNames.length;
    }
    public int getRowCount(){
        return data.length;
    }
    public String getColumnName(int col){
        return columnNames[col];
    }
    public Object getValueAt(int row,int col){
        return data[row][col];
    }
    public void setValueAt(Object value,int row,int col){

        int numRows=getRowCount();
        int numCols=getColumnCount();
        for(int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                System.out.print(" "+data[i][j]);
            }
        }
    }
}
class JSDK extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l2,l;
    JTextField t1,t2;
    JButton b1,b2;
    public JSDK(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("3f207a533aab04161138c212[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,50));
        l1=new JLabel("教职工号");
        l2=new JLabel("该课程号");
        t1=new JTextField("",10);
        t2=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(b1);
        p.add(b2);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")||t2.getText().equals("")){JOptionPane.showMessageDialog(this,"教职工号或课程号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="INSERT INTO 代课表(教职工号,课程号)  VALUES('"+t1.getText()+"','"+t2.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");t2.setText("");
                JOptionPane.showMessageDialog(this,"教师代课成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            JS js=new JS("教师界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class DLJSXX extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l;
    static JTextField t;
    JButton b1,b2;
    public DLJSXX(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("3f207a533aab04161138c212[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        l1=new JLabel("教职工号");
        t=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t.getText().equals("")){JOptionPane.showMessageDialog(this,"教职工号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                this.dispose();
                JSXX gly=new JSXX("教师信息界面");
                gly.setLocation(400, 200);
                gly.setSize(500,300);
                gly.setVisible(true);}
        }
        if(e.getSource()==b2){
            this.dispose();
            JS js=new JS("教师界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class MyTableModel1 extends AbstractTableModel{
    String jsnum,jsname,sex,age,work,kcnum,kcname,score1,kctime,kcaddress,score2;
    final String[] columnNames={"教职工号","姓名","性别","年龄","职称","课程号","课程名","学分","上课时间","上课地点","效果"};
    Object[][]data=new Object[100][11];
    public MyTableModel1(){
        int m=0;
        String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
        Connection con;
        String sql;
        Statement stmt;
        try{
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        }catch(java.lang.ClassNotFoundException e){
        }
        try{
            con=DriverManager.getConnection(url,"SQS","200628");
            stmt=con.createStatement();
            sql="SELECT * FROM 教师_课程  WHERE 教职工号='"+DLJSXX.t.getText()+"'";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                jsnum=rs.getString(1);
                jsname=rs.getString(2);
                sex=rs.getString(3);
                age=rs.getString(4);
                work=rs.getString(5);
                kcnum=rs.getString(6);
                kcname=rs.getString(7);
                score1=rs.getString(8);
                kctime=rs.getString(9);
                kcaddress=rs.getString(10);
                score2=rs.getString(11);
                data[m][0]=jsnum;data[m][1]=jsname;data[m][2]=sex;data[m][3]=age;data[m][4]=work;data[m][5]=kcnum;data[m][6]=kcname;data[m][7]=score1;data[m][8]=kctime;data[m][9]=kcaddress;data[m++][10]=score2;
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
        }
    }
    public int getColumnCount(){
        return columnNames.length;
    }
    public int getRowCount(){
        return data.length;
    }
    public String getColumnName(int col){
        return columnNames[col];
    }
    public Object getValueAt(int row,int col){
        return data[row][col];
    }
    public void setValueAt(Object value,int row,int col){

        int numRows=getRowCount();
        int numCols=getColumnCount();
        for(int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                System.out.print(" "+data[i][j]);
            }
        }
    }
}
class JSXX extends JFrame implements ActionListener{
    JPanel p;
    JButton b1;
    MyTableModel1 mt;
    JTable t;
    JScrollPane s;
    public JSXX(String str){
        super(str);
        setLayout(new BorderLayout());
        mt=new MyTableModel1();
        t=new JTable(mt);
        s=new JScrollPane(t);
        p=new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,10));
        b1=new JButton("返回上级");
        b1.addActionListener(this);
        p.add(b1);
        add(p,"South");
        add(s,"Center");
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            DLJSXX js=new DLJSXX("查询自己信息界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}
class XSCJ extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l2,l3,l;
    JTextField t1,t2,t3;
    JButton b1,b2;
    public XSCJ(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("http_imgload[8].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,30));
        l1=new JLabel("学    号");
        l2=new JLabel("课程号");
        l3=new JLabel("成    绩");
        t1=new JTextField("",10);
        t2=new JTextField("",10);
        t3=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(l3);
        p.add(t3);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")||t2.getText().equals("")||t3.getText().equals("")){JOptionPane.showMessageDialog(this,"学号.课程号.成绩不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="UPDATE 选课表 SET 成绩='"+t3.getText()+"' WHERE(学号='"+t1.getText()+"' AND 课程号='"+t2.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");t2.setText(""); t3.setText("");
                JOptionPane.showMessageDialog(this,"管理学生成绩成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            JS js=new JS("管理学生界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}


class XSXK1 extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l2,l;
    JTextField t1,t2;
    JButton b1,b2;
    public XSXK1(String str){
        super(str);
        p=new JPanel();
        i=new ImageIcon("http_imgload[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,50));
        l1=new JLabel("学生学号");
        l2=new JLabel("该课程号");
        t1=new JTextField("",10);
        t2=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t1);
        p.add(l2);
        p.add(t2);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t1.getText().equals("")||t2.getText().equals("")){JOptionPane.showMessageDialog(this,"学生学号或课程号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{
                String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
                Connection con;
                String sql;
                Statement stmt;
                try{
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                }catch(java.lang.ClassNotFoundException e1){
                    e1.printStackTrace();
                }
                try{
                    con=DriverManager.getConnection(url,"SQS","200628");
                    stmt=con.createStatement();
                    sql="INSERT INTO 选课表(学号,课程号) VALUES('"+t1.getText()+"','"+t2.getText()+"')";
                    stmt.executeUpdate(sql);
                    stmt.close();
                    con.close();
                }catch(SQLException e2){
                    e2.printStackTrace();
                }
                t1.setText("");t2.setText("");
                JOptionPane.showMessageDialog(this,"学生选课成功!","提示",JOptionPane.INFORMATION_MESSAGE);}
        }
        if(e.getSource()==b2){
            this.dispose();
            XS xs=new XS("学生界面");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);
        }
    }
}
class DLXSXX extends JFrame implements ActionListener{
    JPanel p;
    ImageIcon i;
    JLabel l1,l;
    static JTextField t;
    JButton b1,b2;
    public DLXSXX(String str){
        super(str);
        p=new JPanel();i=new ImageIcon("a63e9ee8678507772797911b[1].jpg");
        l=new JLabel(i);
        l.setBounds(0, 0,i.getIconWidth(),i.getIconHeight());
        getLayeredPane().add(l, new Integer(Integer.MIN_VALUE));
        p=(JPanel) this.getContentPane();
        p.setOpaque(false);
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,70));
        l1=new JLabel("学生学号");
        t=new JTextField("",10);
        b1=new JButton("确定");
        b2=new JButton("返回");
        b1.addActionListener(this);
        b2.addActionListener(this);
        p.add(l1);
        p.add(t);
        p.add(b1);
        p.add(b2);

    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            if(t.getText().equals("")){JOptionPane.showMessageDialog(this,"学生学号不能为空!","提示",JOptionPane.WARNING_MESSAGE);}
            else{  this.dispose();
                XSXX gly=new XSXX("学生信息界面");
                gly.setLocation(400, 200);
                gly.setSize(500,300);
                gly.setVisible(true);}
        }
        if(e.getSource()==b2){
            this.dispose();
            XS xs=new XS("学生界面");
            xs.setLocation(400, 200);
            xs.setSize(500,300);
            xs.setVisible(true);
        }
    }
}
class MyTableModel2 extends AbstractTableModel{
    String xsnum,xsname,sex,age,profess,kcnum,kcname,score1,kctime,kcaddress,score2;
    final String[] columnNames={"学号","姓名","性别","年龄","专业","课程号","课程名","学分","上课时间","上课地点","成绩"};
    Object[][]data=new Object[100][11];
    public MyTableModel2(){
        int m=0;
        String url="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=学生选课系统";
        Connection con;
        String sql;
        Statement stmt;
        try{
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        }catch(java.lang.ClassNotFoundException e){
        }
        try{
            con=DriverManager.getConnection(url,"SQS","200628");
            stmt=con.createStatement();
            sql="SELECT * FROM 学生_课程 WHERE 学号='"+DLXSXX.t.getText()+"'";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()){
                xsnum=rs.getString(1);
                xsname=rs.getString(2);
                sex=rs.getString(3);
                age=rs.getString(4);
                profess=rs.getString(5);
                kcnum=rs.getString(6);
                kcname=rs.getString(7);
                score1=rs.getString(8);
                kctime=rs.getString(9);
                kcaddress=rs.getString(10);
                score2=rs.getString(11);
                data[m][0]=xsnum;data[m][1]=xsname;data[m][2]=sex;data[m][3]=age;data[m][4]=profess;data[m][5]=kcnum;data[m][6]=kcname;data[m][7]=score1;data[m][8]=kctime;data[m][9]=kcaddress;data[m++][10]=score2;
            }
            stmt.close();
            con.close();
        }catch(SQLException e){
        }
    }
    public int getColumnCount(){
        return columnNames.length;
    }
    public int getRowCount(){
        return data.length;
    }
    public String getColumnName(int col){
        return columnNames[col];
    }
    public Object getValueAt(int row,int col){
        return data[row][col];
    }
    public void setValueAt(Object value,int row,int col){

        int numRows=getRowCount();
        int numCols=getColumnCount();
        for(int i=0;i<numRows;i++){
            for(int j=0;j<numCols;j++){
                System.out.print(" "+data[i][j]);
            }
        }
    }
}
class XSXX extends JFrame implements ActionListener{
    JPanel p;
    JButton b1;
    MyTableModel2 mt;
    JTable t;
    JScrollPane s;
    public XSXX(String str){
        super(str);
        setLayout(new BorderLayout());
        mt=new MyTableModel2();
        t=new JTable(mt);
        s=new JScrollPane(t);
        p=new JPanel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER,110,10));
        b1=new JButton("返回上级");
        b1.addActionListener(this);
        p.add(b1);
        add(p,"South");
        add(s,"Center");
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            this.dispose();
            DLXSXX js=new DLXSXX("查询自己信息界面");
            js.setLocation(400, 200);
            js.setSize(500,300);
            js.setVisible(true);
        }
    }
}


