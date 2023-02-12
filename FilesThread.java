import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;


public class FilesThread implements Runnable{
    public Thread fileThread;
    private File[] chunkFiles;
    private String threadName;

    public FilesThread(File[] chunkFiles, int threadNumber){
        //create thread
        threadName = ("Thread" + Integer.toString(threadNumber));
        fileThread = new Thread (this, threadName); //child thread
        this.chunkFiles = chunkFiles;
    }

    //entry point
    public void run(){
        //here is where we call the methods of the FilesetProcessor class to process the files in different threads

        //print all the files that we have read
        String loggingFileName = "/home/guser/fileset/" + this.threadName + ".log";
        try {
            FileHandler loggingFileHandler = new FileHandler(loggingFileName, true);

            Logger processingLogger = Logger.getLogger(threadName);
            processingLogger.addHandler(loggingFileHandler);

            for (File file: chunkFiles){

                String fileName = file.getName();
                String fileExtension = fileName.substring(fileName.indexOf(".") + 1);
                String result = "";


                //tp add a new file type, just add an if clause and the relevant method.
                if (fileExtension.equals("txt")) {
                    result = FilesetProcessor.txtProcessor(file);
                } else if (fileExtension.equals("pdf")) {
                    result = FilesetProcessor.pdfProcessor(file);
                } else if (fileExtension.equals("csv")) {
                    result = FilesetProcessor.csvProcessor(file);
                } else if (fileExtension.equals("docx")) {
                    result = FilesetProcessor.docxProcessor(file);
                }
                processingLogger.info(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        }//run

    }//FilesThread



