package pack1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;
 

public class PrototypeDemo {
 
	
	private static int line_count=0;
	private static int word_count=0;
	private static Thread[] consumers; 
	private static Map<String, Integer> wordCounts = new ConcurrentHashMap<String, Integer>();
	private static Map<String, Integer> threadCounts = new ConcurrentHashMap<String, Integer>();
	private static boolean readingISFinished = false;	
	private static Pattern specialCharsRemovePattern = Pattern.compile("[^a-zA-Z]");
	private static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(1000);	
	static <K,V extends Comparable<? super V>>
	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                int res = -e1.getValue().compareTo(e2.getValue());
	                return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
	public static void main(String args[]) throws InterruptedException {
 
		// Parse arguments
		if (args.length != 3) {
			System.out.println("Parameters: Input File path, Thread Number");
			System.exit(1);
		}
		String inputFile = args[0];
		int nbThreads = Integer.parseInt(args[2]);
 
		// Start timer
		System.out.printf("Execution starting with %d consumer thread(s) ...\n", nbThreads);
		
		
		// Create array to store the consumer threads
		consumers = new Thread[nbThreads];
 
		// Create and start Producer thread
		Thread producer = new Thread(new Producer(inputFile));
		producer.start();
		
		// Create and start Consumer Threads
		for (int i = 0; i < nbThreads; i++) {
			consumers[i] = new Thread(new Consumer());

			threadCounts.put(consumers[i].getName(), 0);
			consumers[i].start();
		}
		
		// Wait for all threads to finish
		producer.join();
		
		for (int i = 0; i < nbThreads; i++) {
			consumers[i].join();
		}
		
		Map<String, Integer> ordered = new TreeMap<String, Integer>(wordCounts);

		
		for(Map.Entry<String,Integer> entry : entriesSortedByValues(ordered)) {
			  String key = entry.getKey();
			  Integer value = entry.getValue();
			  word_count=word_count+ entry.getValue();
			  System.out.println(key + " " + value);
		}	
		
		System.out.println(" ***************" );
		for(Map.Entry<String,Integer> entry : threadCounts.entrySet()) {
			  String key = entry.getKey();
			  Integer value = entry.getValue();
			  System.out.println(key + " Count : " + value);
		}	
		System.out.println(" ***************" );
		System.out.printf("Sentence Count : %d\n",line_count);	
		System.out.printf("Word Count all :  %d\n",word_count);
		System.out.printf("Avg. Word Count : %d\n",word_count/line_count);
		System.out.printf("Word Count ( distinct) :  %d\n",wordCounts.size());
		
	}
	 
	public static class Producer implements Runnable {
 
		private String inputFile;
 
		public Producer(String inputFile) {
			this.inputFile = inputFile;
		}
 
		@Override
		public void run() {
			File input = new File(inputFile);
			try (BufferedReader br = new BufferedReader(new FileReader(input));) {
				String line;
				while ((line = br.readLine()) != null) {
					blockingQueue.put(line);
					line_count++;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			readingISFinished = true;
		}
 
	}
  
	public static class Consumer implements Runnable {
 
		@Override
		public void run() {
			
			while (readingISFinished==false || !blockingQueue.isEmpty()) {
  
				String line = blockingQueue.poll();
				if (line == null) continue;
				 
				String[] words = specialCharsRemovePattern.matcher(line)
						.replaceAll(" ").split("\\s+");
				String threadname=Thread.currentThread().getName();
				
				int count_thread = threadCounts.containsKey(threadname) ? threadCounts.get(threadname) + 1 : 1;
			    threadCounts.put(threadname, count_thread);
				for (String word : words) {
					int count = wordCounts.containsKey(word) ? wordCounts.get(word) + 1 : 1;
					wordCounts.put(word, count);
				}
			}
		}
 
	}
 
}