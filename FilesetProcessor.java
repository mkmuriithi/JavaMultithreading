
import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.concurrent.*;


public class FilesetProcessor {

    //
    public static final String PDFS_PATH = "/home/guser/fileset/pdf-files";
    public static final String CSVS_PATH = "/home/guser/fileset/csv-files";
    public static final String TXTS_PATH = "/home/guser/fileset/txt-files";
    public static final String DOCXS_PATH = "/home/guser/fileset/docx-files";
    public static void main(String[] args) {
        System.out.println("Hello there World");

        String filesDirectoryPath = "/home/guser/fileset/";
        File filesDirectory = new File(filesDirectoryPath);
        File[] allFiles  = filesDirectory.listFiles(); //gets all files in directory
        if(allFiles == null){
            System.out.println("There are no files to process");
            return;
        }
        // write code to read a single file
        int numOfThreads = 2;
        int sizeOfChunk = allFiles.length / numOfThreads;

        //create a new thread for each type of file
        ExecutorService es = Executors.newFixedThreadPool(numOfThreads);

        //generate then ranges
        int startIndex = 0;
        int endIndex = startIndex + sizeOfChunk;
        int threadNumber = 0;


        while(endIndex <= allFiles.length){
            es.execute(new FilesThread(
                    Arrays.copyOfRange(allFiles, startIndex, endIndex),
                    threadNumber
            ));

            threadNumber++;
            startIndex = endIndex;
            endIndex += sizeOfChunk;
        }
        //final check if endIndex > length of list
        if(startIndex < allFiles.length){
            //do one more
            es.execute(new FilesThread(
                    Arrays.copyOfRange(allFiles, startIndex, endIndex),
                    threadNumber
            ));
        }

        es.shutdown();
        System.out.println("done");




    }//main
    public static String pdfProcessor(File file){
        //move the given files to a predetermined location

        Path destinationPath = Paths.get(PDFS_PATH + "/" + file.getName()); //throws InvalidPathException
        Path sourcePath = file.toPath();
        //move to folder

        try {
            Files.move(sourcePath, destinationPath);
        }catch (IOException e){
            System.err.println("Unable to move the file to the folder");
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        return "Processed File: " + file.getName();
    }//pdfProcessor

    public static String txtProcessor(File file){
        Path destinationPath = Paths.get(TXTS_PATH + "/" + file.getName()); //throws InvalidPathException
        Path sourcePath = file.toPath();


        try {
            Files.move(sourcePath, destinationPath);
        }catch (IOException e){
            System.err.println("Unable to move the file to the folder");
            System.err.println(e.getCause());
        }
        return "Processed File: " + file.getName();
    }//txtProcessor
    public static String docxProcessor(File file){
        Path destinationPath = Paths.get(DOCXS_PATH + "/" + file.getName()); //throws InvalidPathException
        Path sourcePath = file.toPath();
        //move to folder

        try {
            Files.move(sourcePath, destinationPath);
        }catch (IOException e){
            System.err.println("Unable to move the file to the folder");
        }
        return "Processed File: " + file.getName();
    }//docxProcessor
    public static String csvProcessor(File file){
        Path destinationPath = Paths.get(CSVS_PATH + "/" + file.getName()); //throws InvalidPathException
        Path sourcePath = file.toPath();
        //move to folder

        try {
            Files.move(sourcePath, destinationPath);
        }catch (IOException e){
            System.err.println("Unable to move the file to the folder");
        }
        //return processed file
        return "Processed File: " + file.getName();
    }//csvProcessor
}


