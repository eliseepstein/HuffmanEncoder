import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Huffman {

	// an ArrayList that holds the elements of the tree and after the tree is made
	// only holds the node of the tree
	private static ArrayList<HuffmanNode> huffNodesTree = new ArrayList<HuffmanNode>();

	// an ArrayList that has all the nodes in them from the tree
	private static ArrayList<HuffmanNode> huffNodes = new ArrayList<HuffmanNode>();

	/*
	 * Note: I used Arraylists instead of LinkedLists because I thought that it
	 * would be easier to access elements from an arraylist as it has a fixed size
	 * and resizes the arraylist if the size gets too small.
	 * 
	 */

	// a method that updates the frequencies of the file inputted for the variable
	// fileToMakeEncodingWith
	public static void updateFrequencies(String fileToMakeEncodingWith) throws IOException {
		FileReader reader = new FileReader(fileToMakeEncodingWith);
		HashMap<Character, Integer> frequencyTable = new HashMap<>();

		// reading the first character
		Character c = (char) reader.read();

		// while the current character isn't an end of text character
		while (c != -1 && c != 65535) {

			// if the character doesn't already appears in the frequency table, add the
			// character to the frequency table
			if (!frequencyTable.containsKey(c)) {
				frequencyTable.put(c, 1);
			}

			// otherwise add 1 to the frequency of the character
			else {
				frequencyTable.put(c, frequencyTable.get(c) + 1);
			}
			c = (char) reader.read();
		}

		// adds each node that has frequencies to the huffNodesTree arraylist
		for (Map.Entry entry : frequencyTable.entrySet()) {
			huffNodesTree
					.add(new HuffmanNode((Character) entry.getKey(), (Integer) entry.getValue(), null, null, null));
		}
	}

	// a method that merges two nodes and returns the new root
	public static HuffmanNode mergeNodes(HuffmanNode a, HuffmanNode b) {
		return new HuffmanNode(null, a.getFrequency() + b.getFrequency(), a, b, null);
	}

	// a method that makes a HuffmanTree and returns the root node
	public static HuffmanNode makeHuffmanTree() {

		// sorts the tree based on frequency
		Collections.sort(huffNodesTree);

		// merges merges nodes into a parent node until there is only 1 node left (or 0
		// nodes if huffNodesTree is empty) and sorts the arraylist each time two nodes
		// are merged
		while (huffNodesTree.size() != 0 && huffNodesTree.size() != 1) {
			huffNodesTree.add(mergeNodes(huffNodesTree.remove(0), huffNodesTree.remove(0)));
			Collections.sort(huffNodesTree);
		}
		return huffNodesTree.get(0);
	}

	// a method that creates a file with the inputted name
	public static File createFile(String outputName) throws IOException {
		File output = new File(outputName);
		if (output.createNewFile())
			System.out.println("File Created");
		else {
			PrintWriter writer = new PrintWriter(output);
			writer.print("");
			writer.close();
			System.out.println("File cleared");
			return output;
		}
		return null;
	}

	// a method that updates the character encodings of the nodes in the huffNodes
	// arraylist
	public static void updateCharacterEncodings() {

		// top node of the tree
		HuffmanNode node = makeHuffmanTree();

		// if the node is null
		if (node != null) {
			node.setEncoding("");
			updateCharacterEncodingsNotFirst(node);
		}
	}

	// a helper method to update the encodings of not the root node
	public static void updateCharacterEncodingsNotFirst(HuffmanNode node) {

		// setting the roots encoding

		if (node != null) {
			// if the node has a character, add it to the huffNodes list
			if (node.getCharacter() != null) {
				huffNodes.add(node);
			} 
			else {

				// if the left node isn't null add 0 to the encoding of the current node to get
				// the encoding of the left node
				if (node.getLeft() != null) {
					node.getLeft().setEncoding(node.getEncoding() + 0);
					updateCharacterEncodingsNotFirst(node.getLeft());
				}

				// if the right node isn't null add 1 to the encoding of the current node to get
				// the encoding of the right node
				if (node.getRight() != null) {
					node.getRight().setEncoding(node.getEncoding() + 1);
					updateCharacterEncodingsNotFirst(node.getRight());
				}
			}
		}
	}

	// a method that writes an encoding table with the file name of the inputted
	// string with format character: frequency: encoding
	public static void writeEncodingTable(String file) throws IOException {
		PrintWriter writer = new PrintWriter(file);
		createFile(file);

		// prints out the character to the document with the format character:
		// frequency: encoding
		for (HuffmanNode node : huffNodes) {
			if (node.getFrequency() != 0) {
				writer.println("" + node.getCharacter() + " : " + node.getFrequency() + " : " + node.getEncoding());
			}
		}
		writer.close();
	}

	// a method that writes the output file with the name of the inputted outputFile
	// and encodes the input file
	public static void writeOutputFile(String inputFile, String outputFile) throws IOException {
		PrintWriter writer = new PrintWriter(createFile(outputFile));
		FileReader reader = new FileReader(inputFile);
		Character c = (char) reader.read();
		StringBuilder builder = new StringBuilder();

		// while the character's value is in the text (not an end of text character)
		while (c != -1 && c != 65535) {

			// a loop that checks if the current node in HuffNodes has the same character as
			// the character in the text and if so appends the encoding of the node
			for (HuffmanNode node : huffNodes) {
				if (node.getCharacter() == c) {
					builder.append(node.getEncoding());
					System.out.println(c + " : " + node.getEncoding());
					break;
				}
			}

			// sets the character to be read to the next character in the text. This
			// character is to go through the for loop next.
			c = (char) reader.read();
		}

		writer.print(builder.toString());
		writer.close();
		reader.close();
	}

	// a method that encodes the inputted files with and outputs the output file and
	// the encoding table
	public static String huffmanEncoder(String inputFileName, String encodingFileName, String outputFileName) {
		try {
			updateFrequencies(encodingFileName);
			System.out.println("update frequencies complete");
		} catch (IOException e) {
			return "File to make encoding with is invalid.";
		}

		updateCharacterEncodings();
		System.out.println("huffman tree and update character encodings complete");

		try {
			writeEncodingTable("EncodingTable.txt");
			System.out.println("write encoding table complete");
		} catch (IOException e) {
			return "The inputted file has a problem with it.";
		}

		try {
			writeOutputFile(inputFileName, outputFileName);
			System.out.println("write output file complete");
		} catch (IOException e) {
			return "One of the inputted files have a problem.";
		}

		// insert the code below to get the decoding of the encoding

		try {
			decode(huffNodesTree.get(0), outputFileName);
		} catch (IOException e) {
			return "There is a problem with decoding";
		}

		// calculating savings

		double originalBits = 0;
		double finalBits = 0;
		double savedBits = 0;

		// for all nodes w/frequencies, if the node has an encoding then update the
		// originalBits, savedBits, and finalBits
		for (HuffmanNode node : huffNodes) {
			if (node != null && node.getEncoding() != null) {
				originalBits = originalBits + 8 * node.getFrequency();
				savedBits = savedBits + node.getFrequency() * (8 - node.getEncoding().length());
				finalBits = finalBits + node.getFrequency() * node.getEncoding().length();
			}
		}

		System.out.println("The original number of non-encoded bits is: " + originalBits);
		System.out.println("The number of encoded bits is: " + finalBits);
		System.out.println(savedBits + " bits were saved");
		System.out.println(savedBits / originalBits * 100 + " percent bits were saved.");

		return "Encoding is done.";

	}

	// the main method that takes in the inputted files
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println(
					"The formatting of the command lines are wrong. They should be the inputFileName, the encodingFileName,and then the outputFileName");
		} else {
			System.out.println("Input file name: " + args[0]);
			System.out.println("Encoding file name: " + args[1]);
			System.out.println("Output file name: " + args[2]);
			System.out.println(huffmanEncoder(args[0], args[1], args[2]));
		}
	}

	// a method I made to decode the encoding to make sure I get Gadsby back
	public static void decode(HuffmanNode node, String coded) throws IOException {

		// the string as the argument for the createFile method should be the name of
		// the new file that contains
		FileReader reader = new FileReader(coded);
		PrintWriter writer = new PrintWriter(createFile("Decode.txt"));
		Character c = (char) reader.read();

		// while the character is a character in the text and not an end of text
		// character
		while (c != -1 && c != 65535) {

			// while the node is an internal node
			while (node.getCharacter() == null) {
				if (c == '0') {
					node = node.getLeft();
				} else {
					node = node.getRight();
				}
				c = (char) reader.read();
			}

			// print the character of the decoding
			writer.print(node.getCharacter());

			// reset the node to the top of the tree so the program can traverse again
			node = huffNodesTree.get(0);
		}
		writer.close();
		reader.close();
	}

}
