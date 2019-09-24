
import java.io.*;
import java.util.ArrayList;

public  class  Sudoku
{


    private static String input;//输入文件名
    private static String output;//输出文件名
    private static int gong;//宫格数
    private static int n;//盘面数
    private static  int trans[][] = new int [9][9];//中间数组
    static ArrayList<int[][]> shudu = new ArrayList<>();//待解决的数组
    static ArrayList<int[][]> result = new ArrayList<>();//已解决的数组

//    行列宫检查
    public  static  Boolean islegal(int sudu[][], int x, int y, int num, int gongge){

         for (int i = 0; i < gongge; i++) {
            if (i != x && sudu[i][y] == num) {
                return false;
            }
            if (i != y && sudu[x][i] == num) {
                return false;
            }
        }

        if(gongge==9){
            int x_position = x / 3 * 3;
            int y_position = y / 3 * 3;

            for (int i = x_position; i < x_position + 3; i++) {
                for (int j = y_position; j < y_position + 3; j++) {
                    if ( sudu[i][j] == num) {
                        return false;
                    }
                }
            }
        }

        if(gongge==8){

            int x_position = x / 4 * 4;
            int y_position = y / 2 * 2;
            for (int i = x_position; i < x_position + 4; i++) {
                for (int j = y_position; j < y_position + 2; j++) {

                    if ( sudu[i][j] == num) {
                        return false;
                    }
                }
            }
        }
        if(gongge==6){

            int x_position = x / 2 * 2;
            int y_position = y / 3 * 3;

            for (int i = x_position; i < x_position + 2; i++) {
                for (int j = y_position; j < y_position + 3; j++) {
                    if (sudu[i][j] == num) {
                        return false;
                    }
                }
            }
        }
        if(gongge==4){
            int x_position = x / 2 * 2;
            int y_position = y / 2 * 2;
            for (int i = x_position; i < x_position + 2; i++) {
                for (int j = y_position; j < y_position + 2; j++) {

                    if (sudu[i][j] == num) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

//深度优先搜索
    public static  Boolean dfs(int num,int m){
        if(num==(m*m))
            return true;
        int i=num/m,j=num%m;
        if(trans[i][j]!=0) return dfs(num+1,m);
        for(int nn=1; nn<=m; nn++)
        {
            if(islegal(trans,i,j,nn,m))
            {
                trans[i][j]=nn;
                if(dfs(num+1,gong)) return true;
                trans[i][j]=0;

            }
        }
        return false;
    }

//分割命令行  初始化成员变量
    public static void loadArgs(String args[]) {
          input=args[5];
          output=args[7];
          gong  = Integer.valueOf(args[1]).intValue();
          n = Integer.valueOf(args[3]).intValue();

    }

    public  static  void main(String[] args) {
        args = new String[]{"-m","8","-n","5","-i","src/input.txt","-o","src/output.txt"};
        loadArgs(args);
//读入文件 用try捕捉异常
        try {
            File file=new File(input);
            //用filereader类逐字符读入文本
            BufferedReader br = new BufferedReader(new FileReader((file)));
            for(int tag = 0; tag < n; tag++){
                int array[][] = new int[9][9];
                int temp;
                //连续读入 遇到空格或者回车就跳过
                for(int i = 0;i < gong ; i++){
                    for(int j = 0 ;j < gong ;j++){

                        temp = br.read();
                        if ( (((char) temp) != '\n') &&(((char) temp) != ' '))
                            array[i][j]= ((char) temp)-48;

                        else
                            j--;

                    }
                    br.readLine();
                }
                br.readLine();
                shudu.add(array);
            }
            br.close();

        } catch(Exception e){
            e.printStackTrace();
        }
//解决问题 每解决一个二维数组shudu就把结果添加到已解决数组result
        for(int i=0;i<n;i++){
            trans = shudu.get(i);
            dfs(0,gong);
            result.add(trans);
        }
//写入txt,用try捕捉异常*
        try{
            File file = new File(output);
            if(!file.exists()){
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(int tag=0;tag<result.size();tag++){
                int [][] Final  = result.get(tag);
                    for (int i = 0;i<gong;i++){
                        for(int j =0 ; j<gong ;j++){
                            bw.write(Final[i][j]+"");
                            bw.write(" ");
                        }
                        bw.newLine();
                    }
                    bw.newLine();
                    bw.flush();//把流缓冲冲入到文本中
                }
                bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
