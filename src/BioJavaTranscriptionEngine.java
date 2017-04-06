import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * This program demonstates using the BioJava transcription Engine to work with a Fasta file.
 */
public class BioJavaTranscriptionEngine {

    public static void main(String[] args) {
        //let's start by declaring some data structures to hold our data
        //LinkedHashMap will be used to hold the dnaSequence to read from the FASTA file
        LinkedHashMap<String, DNASequence> dnaSequence = null;

        //let's get the FASTA filename from the user.. using JFileChooser
        //TODO: proper handling of user cancel and Asynchronous display
        final JFileChooser fc = new JFileChooser();
        int returnValue = fc.showOpenDialog(null);
        //once we have the filename, use FastaReaderHelper (a convenience class provided by BioJava to work with FASTA files
        try{
            dnaSequence = FastaReaderHelper.readFastaDNASequence(new File(fc.getSelectedFile().getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            System.out.println("opened the FASTA file successfully");
        }
    }

}
