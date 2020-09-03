import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/*
*
*
*                                       字节流
*
*FileInputStream类
*FileOutputStream类
*
*
*
* */
public class TestIO {
    public static void main(String[] args) throws IOException {
        copyFile("d:/testFile/kenan.jpg", "d:/testFile/kenan2.jpg");
    }

    private static void copyFile(String srcPath, String destPath) throws IOException {
        //1.先打开文件，才能够读写（创建InputStream/OutputStream对象的过程）
        //FileputStream是InputStream里的一个类
        FileInputStream fileInputStream = new FileInputStream(srcPath);
        FileOutputStream fileOutputStream = new FileOutputStream(destPath);
        //2.需要读取srcPath对应的内容
        byte[] buffer = new byte[1024];//单词读取数据内容是有上限的（缓冲区的长度如1024字节），需要把程序读完就要搭配循环使用
        //如果读完的len是 -1 说明读取结束，循环就结束
        int len = -1;
        //read和write方法都有三个
        //第一个是一次 读/写 一个字节
        //第二个是一次 读/写 若干个字节，会把buffer这个数组的所有字节都写进去
        //第三个是一次 读/写 若干个字节，从buffer的 off下标开始，然后写len个字节
        while ((len = fileInputStream.read(buffer)) != -1){
        //读取成功，就把读取的内容写到另一个destPath文件中
            
        //3.吧读取的内容写到destPath对应的文件中
            fileOutputStream.write(buffer, 0, len);
        }
        fileInputStream.close();
        fileOutputStream.close();

    }


    //copyFile方法在某些特殊的情况下触发IOException异常程序就不可能跑到fileInputStream.close();fileOutputStream.close();
    //此时没有关闭文件就会造成资源泄露 所以要用finally保证close方法始终执行
    private static void copyFile2(String srcPath, String destPath) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(srcPath);
            fileOutputStream = new FileOutputStream(destPath);

            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fileInputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //这里再加上两个if判断是因为在开始实例化时如果传进来的srcPath destPath路径文件不存在
                //那么就会触发FileNotFoundException异常 那么说明fileInputStream和fileOutputStream
                //尚未构造成功还是null，接下来在用close方法就会触发空指针异常
                //所以要在使用close方法之前判断一下fileInputStream和fileOutputStream是否为null
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void testFile3(){
        //try语句执行完毕之后自动调用close方法
        try(FileInputStream fileInputStream = new FileInputStream("d:/testFile/kenan.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream("d:/testFile/kenan2.jpg")){
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fileInputStream.read(buffer)) != -1){
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
