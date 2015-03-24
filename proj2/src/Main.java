
public class Main {

    public static void main(String[] args) {
        int runsize=7, numfiles=2;
        String tempdir="standard_directory", outputfilename="output", inputfilename="input";

        for (int i = 0; i < args.length; i++) {
            //System.out.println(args[i]);
            if(args[i].contains("-r"))
                runsize = Integer.parseInt(args[i+1]);
            if(args[i].contains("-k"))
                numfiles = Integer.parseInt(args[i+1]);
            if(args[i].contains("-d"))
                tempdir = args[i+1];
            if(args[i].contains("-o"))
                outputfilename = args[i+1];
        }

        if ((args.length>0)&&(!(args[args.length-2].contains("-")))) {
            inputfilename = args[args.length - 1];
        }

        System.out.println("runsize: "+runsize+"\nnumfiles: "+numfiles+"\ntempdir: "+tempdir+"\noutputfilename: "
                           +outputfilename+"\ninputfilename: "+inputfilename);
    }
}
