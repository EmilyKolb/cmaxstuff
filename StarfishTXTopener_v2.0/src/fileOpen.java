import javafx.stage.FileChooser;

import java.awt.*;
import java.beans.EventHandler;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class fileOpen {
    private String inputFile;
    private boolean openedOnce = false;
    private Scanner s = null;
    private Scanner t = null;
    boolean firstDescription = true;

    public fileOpen(String inputFile){
        this.inputFile = inputFile;
        try{

            s = new Scanner(new File(inputFile));
        }
        catch(Exception e){
            System.out.println("There was an error opening the file.");
        }

        try{

            t = new Scanner(new File(inputFile));
        }
        catch(Exception e){
            System.out.println("There was an error opening the file.");
        }

        String firstWord = s.next();

        if (firstWord.equals("IN")){
            southPark();
        }
        else if(firstWord.equals("TIMEIN:")){
            oakIsland();
        }
        else{
            pawPatrol();
        }

        //wavNameGenerator();
    }

    public String wavNameGenerator(){
        String partOne = wavNameHelper(8);
        String partTwo = wavNameHelper(4);
        String partThree = wavNameHelper(4);
        String partFour = wavNameHelper(4);
        String partFive = wavNameHelper(4)+wavNameHelper(8);

        String fileName = "{"+partOne + "-" + partTwo + "-" + partThree + "-" + partFour + "-" + partFive+"}.wav";
        //System.out.println(fileName);

        return fileName;
    }

    public String wavNameHelper(int length){
        long Min = 0;
        long Max = 0;

        if (length == 8){
            Min = 268435456;
            Max = 0xFFFFFFFF;
        }

        else if (length == 4){
            Min = 4096;
            Max = 0xFFFF;
        }

        long myVal = Min + (int)(Math.random() * ((Max - Min) + 1));
        String hex = Long.toHexString(myVal);
        String upperHexDig = hex.toUpperCase();

        return upperHexDig;
    }

    public boolean southPark(){
        boolean hasRun = false;

        while(s.hasNext()){
            String line = s.nextLine();
            String[] chunks = line.split("\t");
            String inTime = chunks[0];
            inTime = inTime.replace(".",":");
            System.out.println(inTime);

            String outTime = chunks[1];
            outTime = outTime.replace(".",":");
            String descriptionText = chunks[2];

            if (hasRun) {
                writeDescription(inTime,outTime,descriptionText,false);
            }
            hasRun = true;
        }
        writeDescription("inTime","outTime","descriptionText",true);
        return true;
    }

    public boolean oakIsland(){
        Scanner newScan = s.useDelimiter("TIMEIN: ");
        while(newScan.hasNext()){

            String line = newScan.next();
            String[] chunks = line.split("\t");
            String inTime = chunks[0];


            String theRest = chunks[1];
            String[] sepOutTime = theRest.split("\n",2);
            String outTime = sepOutTime[0];
            //outTime = outTime.replaceAll("\\n", "");
            outTime = outTime.replaceAll("\\r", "");
            String descriptionText = sepOutTime[1];
            String newStr = descriptionText.replaceAll("\\R", " ");


            outTime = outTime.replace("TIMEOUT: ", "");
            if (firstDescription){
                inTime = inTime.substring(1);
                //outTime = outTime.substring(1);
                firstDescription = false;
            }
            writeDescription(inTime,outTime,newStr,false);

        }
        writeDescription("inTime","outTime","descriptionText",true);
        return true;

    }
    public boolean pawPatrol(){
        Scanner newScan = t.useDelimiter("\n\\r");

        while (newScan.hasNext()){
            String line = t.next();
            String[] chunks = line.split("\n",2);
            System.out.println("line is "+line);
            System.out.println("chunks zero is "+chunks[0]);

            System.out.println("---------------------------");

        }
        return true;
    }

    public boolean writeDescription(String inTime, String outTime, String descriptionText, boolean atEnd){

        String startOfFile = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<StarfishTechnologies xmlns=\"www.starfish.tv\" FileFormatMajorVersion=\"1\" FileFormatMinorVersion=\"0\" FileContentDateTime=\"2019-01-03T16:55:15.244Z\" FileContentVersion=\"1\">\n" +
                "  <Programme Name=\"VDS_ KNOD_#####_216_title_SFM_20181231\" Description=\"\">\n" +
                "    <Episode Name=\"\" Number=\"1\" SpoolID=\"\"/>\n" +
                "    <DB FileName=\"\"/>\n" +
                "    <Site DatabaseID=\"-1\" Name=\"Advantage Default 23.98fps\" Description=\"\">\n" +
                "      <Quality Q1Description=\"\" Q1IndicatorColour=\"-16777211\" Q1PercentFromIdeal=\"-1\" Q2Description=\"\" Q2IndicatorColour=\"-16777211\" Q2PercentFromIdeal=\"-1\" Q3Description=\"\" Q3IndicatorColour=\"-16777211\" Q3PercentFromIdeal=\"-1\" Q4Description=\"\" Q4IndicatorColour=\"-16777211\" Q4PercentFromIdeal=\"-1\"/>\n" +
                "      <Timing ReadingSpeed=\"800\" CountSpaceAsChar=\"true\" FrameRate=\"9\" MinDuration=\"00:00:00:15\" MinInterval=\"00:00:00:01\" SepInterval=\"00:00:01:00\"/>\n" +
                "      <PanFade DefaultFadeDuration=\"9\" DefaultFadeDepth=\"9\" DefaultPan=\"0\"/>\n" +
                "      <Delivery FileType=\"1\" CreateSubFolder=\"true\">\n" +
                "        <Params DeliveryMechanism=\"0\" ZipFiles=\"false\" UseTempName=\"true\" ParamsName=\"\" ParamsDescription=\"\">\n" +
                "          <FTP FTPActive=\"false\" FTPPort=\"21\" FTPServerName=\"\" FTPUserName=\"\" FTPPassword=\"\" FTPFolder=\"\" EnableFTPServer2=\"false\" FTPPort2=\"21\" FTPServerName2=\"\" FTPPreserveFileDateTime=\"false\" FTPUseLastActiveServer=\"false\" FTPSwitchBackDelay=\"1899-12-30T00:30:00.000Z\"/>\n" +
                "          <UNC UNCUserName=\"\" UNCPassword=\"\"/>\n" +
                "          <SMTP SMTPAuthoriseUser=\"true\" SMTPServer=\"\" SMTPUserName=\"\" SMTPPassword=\"\" SMTPFromAddress=\"\" SMTPFromName=\"\" SMTPSubject=\"\">\n" +
                "            <EmailAddressSet>\n" +
                "              <ToList/>\n" +
                "              <CCList/>\n" +
                "              <BCCList/>\n" +
                "            </EmailAddressSet>\n" +
                "          </SMTP>\n" +
                "          <HTTP HTTPServer=\"\" HTTPPath=\"\" HTTPPort=\"80\" HTTPServer2=\"\" HTTPPath2=\"\" HTTPPort2=\"80\" EnableHTTPServer2=\"false\"/>\n" +
                "        </Params>\n" +
                "      </Delivery>\n" +
                "      <BWAV AddUpdateBWAVData=\"false\" OriginatorName=\"\" OriginatorReference=\"\" UseTimecode=\"true\" DescAudioNum=\"false\" DescAudioIn=\"false\" DescAudioOut=\"false\" DescScript=\"false\" OriginationTimeFromWav=\"true\"/>\n" +
                "      <SingleWav DefaultStartTimecode=\"09:59:55:00\" DeliverDFXP=\"false\" Deliver24bitWAV=\"false\" DitherUpconversion=\"false\"/>\n" +
                "    </Site>\n" +
                "    <WAV RootADFolder=\"\" ADFolder=\"C:\\SWIFTFILES\\Katie\\Spot Checks\\VDS_ KNOD_#####_216_title_SFM_20181231\\\"/>\n" +
                "    <Tx TxStartTime=\"2018-12-20T19:13:21.280Z\" TxDuration=\"1899-12-30T00:00:00.000Z\" TxDurationTC=\"00:00:00:00\" ScheduleID=\"\"/>\n" +
                "    <Publisher Name=\"\"/>\n" +
                "    <Author Creator=\"Unknown User\" CheckedBy=\"Unknown User\" VoicedBy=\"Unknown User\" LastAccessedBy=\"Unknown User\"/>\n" +
                "    <Groups>\n" +
                "      <Group Name=\"\" Number=\"0\" Status=\"-1\" Indicator=\"0\">\n" +
                "        <Comment></Comment>\n" +
                "        <Media Name=\"\" Description=\"\" Server=\"\" ServerID=\"-1\" GroupID=\"-1\" Location=\"C:\\SWIFTFILES\\Katie\\Spot Checks\\VDS_ KNOD_#####_216_title_SFM_20181231\\\" Filename=\"kod 216 H264 W TC.mov\" AspectRatio=\"1\" TVStandard=\"1\" FrameRate=\"9\" FirstVisibleFrameOffset=\"0\" CurrentFrame=\"0\" MediaStartTC=\"00:58:40:00\" MediaEndTC=\"01:49:04:00\" MediaOffsetTC=\"00:00:00:00\" ThumbFolder=\"\" RootThumbFolder=\"\" AudioGraphicFile=\"\" AudioGraphicOffset=\"0\"/>\n" +
                "        <Descriptions>\n" +
                "          <Description AudioNumber=\"0\" AudioStart=\"00:59:55:20\" AudioEnd=\"00:59:56:06\" OverallStart=\"00:59:55:11\" OverallEnd=\"00:59:56:15\">\n" +
                "            <Comment></Comment>\n" +
                "            <Script>Slate</Script>";

        String front = "            <PanFades>\n" +
                "              <PanFade Pan=\"0\" Fade=\"9\" Duration=\"9\" Offset=\"0\"/>\n" +
                "              <PanFade Pan=\"0\" Fade=\"0\" Duration=\"9\" Offset=\"19\"/>\n" +
                "            </PanFades>\n" +
                "            <Takes>\n" +
                "              <Take TakeName=\"\" SelectedFile=\"false\">";
        String wavName = wavNameGenerator();

        String frontB = "</Take>\n" +
        "            </Takes>\n" +
                "          </Description>\n";

        String timing = "<Description AudioNumber=\"0\" AudioStart=\""+inTime+"\" AudioEnd=\""+outTime+"\" OverallStart=\""+inTime+"\" OverallEnd=\""+outTime+"\">";
        String text = "<Comment></Comment><Script>" + descriptionText + "</Script>";
        String ending = "            <PanFades>\n" +
                "              <PanFade Pan=\"0\" Fade=\"39\" Duration=\"9\" Offset=\"0\"/>\n" +
                "              <PanFade Pan=\"0\" Fade=\"0\" Duration=\"9\" Offset=\"41\"/>\n" +
                "            </PanFades>\n" +
                "            <Takes>\n" +
                "              <Take TakeName=\"\" SelectedFile=\"false\">{76BAD0C1-5F30-44F5-ADBE-996BB9061B57}.wav</Take>\n" +
                "            </Takes>\n" +
                "          </Description>\n" +
                "        </Descriptions>\n" +
                "      </Group>\n" +
                "    </Groups>\n" +
                "  </Programme>\n" +
                "</StarfishTechnologies>";



        boolean fileOpened = true;
        PrintWriter toFile = null;
        String xmlFileName = inputFile.replace(".txt",".xml");
        try{
            FileWriter fw = new FileWriter(xmlFileName,true);
            toFile = new PrintWriter(fw);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            fileOpened = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }

        if (fileOpened){
            if (!atEnd) {
                if (!openedOnce) {
                    toFile.println(startOfFile);
                    openedOnce = true;
                }
                toFile.println(front + wavName+ frontB + timing + text);
            }
            else{
                toFile.println(ending);
            }
        }
        toFile.flush();
        toFile.close();
        return fileOpened;
    }


    public static void main(String[] args) {
        String fileName = "";

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            fileName = selectedFile.getAbsolutePath();
        }
        new fileOpen(fileName);
    }
}


