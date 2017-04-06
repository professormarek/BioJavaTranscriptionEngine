import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.AmbiguityRNACompoundSet;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.template.CompoundSet;
import org.biojava.nbio.core.sequence.template.Sequence;
import org.biojava.nbio.core.sequence.transcription.Frame;
import org.biojava.nbio.core.sequence.transcription.TranscriptionEngine;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This program demonstates using the BioJava transcription Engine to work with a Fasta file.
 */
public class BioJavaTranscriptionEngine {

    private static void displayFileWindow(){

        //let's start by declaring some data structures to hold our data
        //LinkedHashMap will be used to hold the dnaSequence to read from the FASTA file
        LinkedHashMap<String, DNASequence> dnaSequences = null;

        //let's get the FASTA filename from the user.. using JFileChooser
        //TODO: proper handling of user cancel and Asynchronous display
        final JFileChooser fc = new JFileChooser();
        int returnValue = fc.showOpenDialog(null);
        //once we have the filename, use FastaReaderHelper (a convenience class provided by BioJava to work with FASTA files
        try{
            dnaSequences = FastaReaderHelper.readFastaDNASequence(new File(fc.getSelectedFile().getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            System.out.println("opened the FASTA file successfully");
        }
        //define the ambiguity compound sets by calling the static method (see notes!)
        AmbiguityDNACompoundSet ambiguityDNACompoundSet = AmbiguityDNACompoundSet.getDNACompoundSet();
        /*
        get the nucleotide compound set from the RNA comound set and store the result
        in a CompoundSet object
         */
        //get the RNA compound set
        CompoundSet<NucleotideCompound> rnaCompoundSet = AmbiguityRNACompoundSet.getDNACompoundSet();

        //initialize the Transcription Engine using the compound sets and a Factory object
        TranscriptionEngine engine = new TranscriptionEngine.Builder()
                .dnaCompounds(ambiguityDNACompoundSet).rnaCompounds(rnaCompoundSet).build();

        //As we discussed in the last lecture, we need a way to deal with the various transcription frames
        //declare an array of Frames to store each of the frames
        Frame[] sixFrames = Frame.getAllFrames();
        //let's use a for-each statement to process each dna sequence in our dnaSequences from the FASTA file
        for(DNASequence dna: dnaSequences.values()){
            //translate each  to create a Map that stores the results of the transcriptions
            Map<Frame, Sequence<AminoAcidCompound>> results = engine.multipleFrameTranslation(dna,sixFrames);
            //display the results
            //for each frame in the array of frames...
            for(Frame frame: sixFrames){
                System.out.println("Translated frame: "+ frame + "  :  " + results.get(frame));
            }
        }

    }


    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            //override the run() method inherited from Runnable
            public void run() {
                displayFileWindow();
            }
        }); // notice this is all inside the function call invokeLater


    }

}
