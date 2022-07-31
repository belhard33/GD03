/******************************************************************************
 *  Group: Team Red
 *  Date: 03 AUG 2022
 *  Course: IT-516 Data Structures & Algorithms
 *  Assignment: GD03 (Live Data Corpus)
 *
 *  Takes 4 line arguments: text file of tweets and four search strings (states, cities or hashtags)
 *  Draws a histogram of the number of times the search strings are mentioned.
 *
 *  Calculates and returns the number of tweets in the file, number of users
 *  mentioned (total and unique), the most mentioned user, average tweet length,
 *  number of hashtags used (total and unique), and most used hashtag. It also give stats
 *  of different states and cities of the USA and stats of different hashtags.
 *
 *  Compile...
 *  javac-algs4 GD03.java
 *
 *  Use...
 *  > java-algs4 GD03 Dataset_1.txt New York LA Chicago Boston
 *  > java-algs4 GD03 Dataset_1.txt tokyo2020 ArtisticGymnastics 7Olympics games
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GD03 {
    public static void main(String[] args) {

        In in = new In(args[0]); // command line text file
        String input = in.readAll(); // reads in command line text file

// CREATE ARRAYLIST OF TWEETS
        Pattern pattern2 = Pattern.compile("(DATE:)(.+)(\\w+)"); // compiles regex expression
        Matcher matcher2 = pattern2.matcher(input); // finds regex matches in file
        ArrayList<String> tweetList = new ArrayList<>();
        while (matcher2.find()) {
            tweetList.add(matcher2.group());
        }

// COUNT NUMBER OF TWEETS
        StdOut.println("> Number of tweets in file = " + tweetList.size());

// CREATE ARRAYLIST OF ALL @mentions

        Pattern pattern = Pattern.compile("\\@\\w+"); // compiles regex expression
        Matcher matcher = pattern.matcher(input); // finds regex matches in file
        ArrayList<String> mentionsList = new ArrayList<>();
        while (matcher.find()) {

            mentionsList.add(matcher.group());
        }
        StdOut.println("> Number of users mentioned in tweets = " + mentionsList.size());

// CREATE TST OF MENTIONS
        TST<Integer> mentions = new TST<>();
        for (int i = 0; i < mentionsList.size(); i++) {
            String key = mentionsList.get(i);
            if (mentions.get(key) == null) {
                mentions.put(key, 1);
            } else {
                int myKey = mentions.get(key) + 1;
                mentions.put(key, myKey);
            }
        }
// PRINT NUMBER OF UNIQUE MENTIONS
        StdOut.println("> Number of unique mentions = " + mentions.size());
        String mostUsed = "";
        Integer timesUsed = 0;
        for (String key : mentions.keys()) {
            if (mentions.get(key) > timesUsed) {
                mostUsed = key;
                timesUsed = mentions.get(key);
            }
        }
// PRINT MOST MENTIONED USER + NUMBER OF TIMES
        StdOut.println("> Most frequently mentioned user: " + mostUsed + " mentioned " + timesUsed + " times");

// CREATE ARRAYLIST OF TWEET TEXT ONLY
        Pattern pattern3 = Pattern.compile("(.+)(\\w+)"); // compiles regex expression
        Matcher matcher3 = pattern3.matcher(input); // finds regex matches in file
        ArrayList<String> tweetText = new ArrayList<>();
        while (matcher3.find()) {
            tweetText.add(matcher3.group());
        }
        tweetText.removeIf(s -> (s.contains("Location:")));

// COUNT NUMBER OF WORDS IN TWEET TEXT
        String words = ("\\s+");
        Pattern wordsPattern = Pattern.compile(words);
        Integer[] textLengths = new Integer[tweetText.size()];
        for (int i = 0; i < tweetText.size(); i++) {
            String text = tweetText.get(i); // first line of tweet text
            Matcher wordsMatch = wordsPattern.matcher(text);
            int wordsCount = 0;
            while (wordsMatch.find()) {
                wordsCount++;
            }
            textLengths[i] = wordsCount;
        }
// CALCULATE AVERAGE WORD LENGTH OF TWEETS
        //calculate sum of all array elements
        int sum = 0;
        for (int i = 0; i < textLengths.length; i++)
            sum = sum + textLengths[i];
        //calculate average value
        double average = sum / textLengths.length;
        System.out.println("> Average tweet length = " + average + " words");

// CREATE ARRAYLIST OF HASHTAGS
        Pattern pattern4 = Pattern.compile("(?:\\s|\\A)[##]+([A-Za-z0-9-_]+)");
        Matcher matcher4 = pattern4.matcher(input);
        ArrayList<String> tweetHashtags = new ArrayList<>();
        while (matcher4.find()) {
            tweetHashtags.add(matcher4.group());
        }

        StdOut.println("> Number of total hashtags used = " + tweetHashtags.size());

// CREATE TST OF HASHTAGS
        TST<Integer> hashtags = new TST<>();
        for (int i = 0; i < tweetHashtags.size(); i++) {
            String key = tweetHashtags.get(i);
            if (hashtags.get(key) == null) {
                hashtags.put(key, 1);
            } else {
                int myKey = hashtags.get(key) + 1;
                hashtags.put(key, myKey);
            }
        }
// PRINT NUMBER OF UNIQUE HASHTAGS
        StdOut.println("> Number of unique hashtags used = " + hashtags.size());
        String mostUsedHash = "";
        Integer timesUsedHash = 0;
        for (String key : hashtags.keys()) {
            if (hashtags.get(key) > timesUsedHash) {
                mostUsedHash = key;
                timesUsedHash = hashtags.get(key);
            }
        }


// PRINT UNIQUE HASHTAGS
        System.out.print("> Sample hashtags = ");
        for (int i = 0; i < 5; i++) {
            System.out.print(tweetHashtags.get(i) + " ");
        }
        System.out.println();

// PRINT MOST USED HASHTAG + NUMBER OF TIMES
        StdOut.println("> Most frequently used hashtag: " + mostUsedHash + " used " + timesUsedHash + " times");

        Pattern pattern5 = Pattern.compile("(Location:)(.+)(,)");
        Matcher matcher5 = pattern5.matcher(input);
        ArrayList<String> tweetLocation = new ArrayList<>();
        while (matcher5.find()) {
            tweetLocation.add(matcher5.group());
        }

        tweetLocation.removeIf(s -> s.equals(" "));
        StdOut.println("> Number of locations used = " + tweetLocation.size());
        System.out.print("> Sample tweet locations = ");
        for (int i = 0; i < 5; i++) {
            System.out.print(tweetLocation.get(i) + " | ");
        }
        System.out.println();

        TST<Integer> locations = new TST<>();
        for (int i = 0; i < tweetLocation.size(); i++) {
            String key = tweetLocation.get(i);
            if (locations.get(key) == null) {
                locations.put(key, 1);
            } else {
                int myKey = locations.get(key) + 1;
                locations.put(key, myKey);
            }
        }

        StdOut.println("> Number of unique locations used = " + locations.size());
        String mostUsedLocation = "";
        Integer timesUsedLocation = 0;
        for (String key : locations.keys()) {
            if (locations.get(key) > timesUsedLocation) {
                mostUsedLocation = key;
                timesUsedLocation = locations.get(key);
            }
        }

        StdOut.println("> Most frequently tweeted from location: " + mostUsedLocation + " done " + timesUsedLocation + " times");

        Pattern pattern6 = Pattern.compile("(/Houston|Pheonix|Philadelphia|San Antonio|San Diego|Dallas|San Jose|Austin|Jacksonville|Fort Worth|" +
                "Columbus|Indianapolis|Charlotte|San Francisco|Seattle|Denver|Long Beach|Mesa|Kansas City|Sacramento|" +
                "Washington, DC|Nashville-Davidson|Oklahoma City|El Paso|Boston|Portland|Las Vegas|Detroit|Memphis|Baltimore|" +
                "Atlanta|Omaha|Miami|Bakersfield|LA|Los Angeles|USA|United States of America|AL|Alabama|AK|Alaska|AZ|Arizona|AR|" +
                "Arkansas|CA|California|CO|Colorado|CT|Connecticut|DE|Delaware|FL|Florida|GA|Georgia|HI|Hawaii|ID|Idaho|IL|" +
                "Illinois|IN|Indiana|IA|Chicago|Iowa|KS|Kansas|KY|Kentucky|LA|Louisiana|ME|Maine|MD|Maryland|MA|Massachusetts|" +
                "MI|Michigan|MN|Minnesota|MS|Mississippi|MO|Missouri|MT|Montana|NE|Nebraska|NV|Nevada|NH|New Hampshire|NJ|" +
                "New Jersey|NM|New Mexico|NY|New York|NC|North Carolina|ND|North Dakota|OH|Ohio|OK|Oklahoma|OR|Oregon|PA|" +
                "Pennsylvania|RI|Rhode Island|SC|South Carolina|SD|South Dakota|TN|Tennessee|TX|Texas|UT|Utah|VT|Vermont|VA|" +
                "Virginia|WA|Washington|WV|West Virginia|WI|Wisconsin|WY|Wyoming/)");
        Matcher matcher6 = pattern6.matcher(tweetLocation.toString());
        ArrayList<String> tweetUsaLocation = new ArrayList<>();
        while (matcher6.find()) {
            tweetUsaLocation.add(matcher6.group());
        }

        //StdOut.println("> USA Tweets from: " + tweetUsaLocation + tweetUsaLocation.size());


        // HISTOGRAM
        Pattern lineArg1 = Pattern.compile(args[1]);
        Pattern lineArg2 = Pattern.compile(args[2]);
        Pattern lineArg3 = Pattern.compile(args[3]);
        Pattern lineArg4 = Pattern.compile(args[4]);
        int countArg1 = 0;
        int countArg2 = 0;
        int countArg3 = 0;
        int countArg4 = 0;
        Matcher matcherArg1 = lineArg1.matcher(input);
        Matcher matcherArg2 = lineArg2.matcher(input);
        Matcher matcherArg3 = lineArg3.matcher(input);
        Matcher matcherArg4 = lineArg4.matcher(input);
        while (matcherArg1.find()) {
            countArg1++;
        }
        while (matcherArg2.find()) {
            countArg2++;
        }
        while (matcherArg3.find()) {
            countArg3++;
        }
        while (matcherArg4.find()) {
            countArg4++;
        }
        System.out.println("> Number of tweets mentioning \"" + args[1] + "\" = " + countArg1);
        System.out.println("> Number of tweets mentioning \"" + args[2] + "\" = " + countArg2);
        System.out.println("> Number of tweets mentioning \"" + args[3] + "\" = " + countArg3);
        System.out.println("> Number of tweets from location: \"" + args[4] + "\" = " + countArg4);

        Integer[] myArgs = {countArg1, countArg2, countArg3, countArg4};
        double myScalingFactor = 0.9;                         // The actual size of the bars being printed
        double myIncrement = (double) 1 / myArgs.length / 2;
        int myMax = 0;
        for (
                int i = 0;
                i < myArgs.length; i++) {
            if (myArgs[i] > myMax) {
                myMax = myArgs[i];
            }
        }

        StdDraw.setCanvasSize(1000, 500);

        StdDraw.setPenColor(Color.RED);
        StdDraw.text(0.5, 0.85, "Frequency Count");
        for (
                int i = 1;
                i <= myArgs.length; i++) {
            double mySize = (double) myArgs[i - 1];
            mySize = mySize / myMax;
            StdDraw.setPenColor(Color.RED);
            StdDraw.filledRectangle((i * 2 * myIncrement) - myIncrement, 0.5 + (myIncrement * mySize * myScalingFactor), myIncrement * myScalingFactor, myIncrement * myScalingFactor * mySize);
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.text((i * 2 * myIncrement) - myIncrement, 0.75, String.valueOf(myArgs[i - 1]));
            StdDraw.text((i * 2 * myIncrement) - myIncrement, 0.45, String.valueOf(args[(i + 1) - 1]));
        }


        Pattern pattern7 = Pattern.compile("(/OlympicGames| games| International games| cricket | football|" +
                "swimming| usa|champion | thechamp| International Teams|Match|Sports| Olympic|" +
                "National games|Tokyo sports|Tokyo2020|ArtisticGymnastics|7Olympics|Olympics|Common Wealth Games | Tokyo 20 | Olympics 2024/)");
        Matcher matcher7 = pattern7.matcher(tweetHashtags.toString());
        ArrayList<String> tweettopHashtags = new ArrayList<>();
        while (matcher7.find()) {
            tweettopHashtags.add(matcher7.group());
        }

        StdOut.println("> Tokyo Hashtags: " + tweetHashtags.size());


        // HISTOGRAM
        Pattern lineArgs1 = Pattern.compile(args[1]);
        Pattern lineArgs2 = Pattern.compile(args[2]);
        Pattern lineArgs3 = Pattern.compile(args[3]);
        Pattern lineArgs4 = Pattern.compile(args[4]);
        int countArgs1 = 0;
        int countArgs2 = 0;
        int countArgs3 = 0;
        int countArgs4 = 0;
        Matcher matcherArgs1 = lineArgs1.matcher(input);
        Matcher matcherArgs2 = lineArgs2.matcher(input);
        Matcher matcherArgs3 = lineArgs3.matcher(input);
        Matcher matcherArgs4 = lineArgs4.matcher(input);
        while (matcherArgs1.find()) {
            countArgs1++;
        }
        while (matcherArgs2.find()) {
            countArgs2++;
        }
        while (matcherArgs3.find()) {
            countArgs3++;
        }
        while (matcherArgs4.find()) {
            countArgs4++;
        }
        System.out.println("> Number of tweets mentioning \"" + args[1] + "\" = " + countArgs1);
        System.out.println("> Number of tweets mentioning \"" + args[2] + "\" = " + countArgs2);
        System.out.println("> Number of tweets mentioning \"" + args[3] + "\" = " + countArgs3);
        System.out.println("> Number of tweets from location: \"" + args[4] + "\" = " + countArgs4);

        Integer[] hashArgs = {countArgs1, countArgs2, countArgs3, countArgs4};
        double hashScalingFactor = 0.9;                         // The actual size of the bars being printed
        double hashIncrement = (double) 1 / hashArgs.length / 2;
        int hashMax = 0;
        for (
                int i = 0;
                i < hashArgs.length; i++) {
            if (hashArgs[i] > hashMax) {
                hashMax = hashArgs[i];
            }
        }

        StdDraw.setCanvasSize(1000, 500);

        StdDraw.setPenColor(Color.RED);
        StdDraw.text(0.5, 0.85, "Frequency Count");
        for (
                int i = 1;
                i <= hashArgs.length; i++) {
            double hashSize = (double) hashArgs[i - 1];
            hashSize = hashSize / hashMax;
            StdDraw.setPenColor(Color.RED);
            StdDraw.filledRectangle((i * 2 * hashIncrement) - hashIncrement, 0.5 + (hashIncrement * hashSize * hashScalingFactor), hashIncrement * hashScalingFactor, hashIncrement * hashScalingFactor * hashSize);
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.text((i * 2 * hashIncrement) - hashIncrement, 0.75, String.valueOf(hashArgs[i - 1]));
            StdDraw.text((i * 2 * hashIncrement) - hashIncrement, 0.45, String.valueOf(args[(i + 1) - 1]));
        }
    }
}
