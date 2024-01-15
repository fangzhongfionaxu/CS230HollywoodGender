import javafoundations.*;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Iterator;
import java.io.*;

/**
 * Reads data from a file and generates a graph based on the movie titles and 
 * casts in it. comes with multiple useful methods, please see each method for detailed
 * explanation.
 *
 * @author Karrington W., Fiona X., Wenhan X.
 * @version Nov 29, 2023
 */
public class HollywoodGraph
{
    private final int NOT_FOUND = -1;

    private AdjListsGraph<Hollywood> graph;
    private LinkedList<Hollywood> hollywoodObjs;
    private String fileName;
    private float percentageOfMoviesPassed;

    /**
     * Constructor for objects of class HollywoodGraph. Instantiates an empty
     * AdjListsGraph and a LinkedList that hold Hollywood objects.
     * Calls the createGraphFromFile method to read from the input file and 
     * creates a graph based on that. 
     * 
     * @param  fileName  the file to read from
     */
    public HollywoodGraph(String fileName)
    {
        graph = new AdjListsGraph<Hollywood>();
        hollywoodObjs = new LinkedList<Hollywood>();
        this.fileName = fileName;

        createGraphFromFile(fileName);
    }

    /**
     * Read from the input file and generate a graph accordingly where each Movie and/or
     * Actor object is an individual vertex. Each movie vertex is connected with each of 
     * its casts by an edge. Each movie and actor will only have one corresponding
     * Movie/Actor object.
     * Create new Movie and Actor objects and add them to a LinkedList that holds objects
     * of type Hollywood.
     *
     * @param  fileName  the file to read from
     */
    public void createGraphFromFile(String fileName)
    {
        try
        {
            Scanner fileScan = new Scanner(new File(fileName));

            fileScan.nextLine();
            while (fileScan.hasNext())
            {
                String[] splitLine = fileScan.nextLine().split(",");
                String title = splitLine[0].substring(1,splitLine[0].length()-1);
                String name = splitLine[1].substring(1,splitLine[1].length()-1);
                String role = splitLine[3].substring(1,splitLine[3].length()-1);
                String gender = splitLine[5].substring(1,splitLine[5].length()-1);

                Actor a = new Actor(name,gender,role,"Actor");
                Movie m = new Movie(title,"Movie");
                int movieIndex = hollywoodObjs.indexOf(m);
                int actorIndex = hollywoodObjs.indexOf(a);

                if(movieIndex == NOT_FOUND)
                {
                    graph.addVertex(m);
                    hollywoodObjs.add(m);
                    if(actorIndex == NOT_FOUND)
                    {
                        graph.addVertex(a);
                        graph.addEdge(m,a);
                        hollywoodObjs.add(a);
                        m.addCast(a);
                        a.addPlayed(m);
                    }
                    else
                    {
                        Actor foundA = (Actor) hollywoodObjs.get(actorIndex);

                        graph.addEdge(m,foundA);
                        m.addCast(foundA);
                        foundA.addPlayed(m);
                    }
                }
                else
                {
                    Movie foundM = (Movie) hollywoodObjs.get(movieIndex);

                    if(actorIndex == NOT_FOUND)
                    {
                        graph.addVertex(a);
                        graph.addEdge(foundM,a);
                        hollywoodObjs.add(a);
                        foundM.addCast(a);
                        a.addPlayed(foundM);
                    }
                    else
                    {
                        Actor foundA = (Actor) hollywoodObjs.get(actorIndex);

                        graph.addEdge(foundM,foundA);
                        foundM.addCast(foundA);
                        foundA.addPlayed(foundM);
                    }
                }
            }
            fileScan.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("The input file is not found.");
        }
    }

    /**
     * saves the graph generated to a TGF file.
     * 
     * @param  fileName  the name of the TGF file you want to save your graph as
     */
    public void saveGraphToTGF(String fileName)
    {
        graph.saveTGF(fileName);
    }

    /**
     * prints out a list of strings of names of the actors in a given movie, and 
     * returns a LinkedList of all the Actor objects in a given movie
     * 
     * Assumption: There are no two movies with the same title.
     * 
     * @param  title  the title of the movie
     * @return    a LinkedList of all the Actor objects
     */
    public LinkedList<Actor> findAllActors(String title)
    {
        LinkedList<Actor> actorList = new LinkedList<Actor>();
        boolean movieExist = false;
        String s = "Here is a list of the names of the Actors in " + title + ":\n";

        for(int i = 0; i < hollywoodObjs.size(); i++)
        {
            Hollywood current = hollywoodObjs.get(i); 

            if(current.getType().equals("Movie") && current.getName().equals(title))
            {
                Movie currentM = (Movie) current;
                movieExist = true;
                actorList = currentM.getCast();
            }
        }

        if(!movieExist)
        {
            System.out.println("The movie you entered does not exist in the data. Returning an empty LinkedList.");
        }
        /*else if(actorList.size() == 0)
        {
        System.out.println("Sorry, we don't have the cast information of the movie you entered.");
        }*/
        else
        {
            for(int i = 0; i < actorList.size(); i++)
            {
                s += actorList.get(i).getName() + "\n";
            }
            System.out.println(s);
        }
        return actorList;
    }

    /**
     * prints out a list of strings of titles of the Movies that a given actor has played in, and 
     * returns a LinkedList of all the Movie objects that a given actor has played in
     * 
     * Assumption: There are no two actors with the same name.
     * 
     * @param  name  the name of the actor
     * @return    a LinkedList of all the Movie objects
     */
    public LinkedList<Movie> findAllMovies(String name)
    {
        LinkedList<Movie> movieList = new LinkedList<Movie>();
        boolean actorExist = false;
        String s = "Here is a list of the titles of the Movie that " + name + " has played in:\n";

        for(int i = 0; i < hollywoodObjs.size(); i++)
        {
            Hollywood current = hollywoodObjs.get(i);

            if(current.getType().equals("Actor") && current.getName().equals(name))
            {
                Actor currentA = (Actor) current;
                movieList = currentA.getPlayed();
                actorExist = true;
            }
        }

        if(!actorExist)
        {
            System.out.println("The actor you entered does not exist in the data. Returning an empty LinkedList.");
        }
        else
        {
            for(int i = 0; i < movieList.size(); i++)
            {
                s += movieList.get(i).getName() + "\n";
            }
            System.out.println(s);
        }
        return movieList;
    }

    /**
     * performs a breadthe first search given the names of two Actors. returns an integer
     * indicating the number of Actors that separate the two given ones. prints out the
     * path (all the nodes, Movie and Actor) from a1 to a2. 
     * 
     * Assumption: the input will be String of names of the actors, as it is the most
     * likely and convenient type of input from a client.
     * 
     * @param  a1  1 of the 2 Actors to check for
     * @param  a2  2 of the 2 Actors to check for
     * 
     * @return    an integer the number of Actors that separate the two given ones, returns
     * -1 if entered same Actor twice, or if Actor(s) does not exist.
     */
    public int findActorsDistance(String a1, String a2)
    {
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayIterator<Hollywood> iter = new ArrayIterator<Hollywood>();
        boolean[] visited = new boolean[graph.getNumVertices()];
        int startIndex = -1;
        int endIndex = -1;

        //checks if the two given names are the same
        if(a1.equals(a2))
        {
            System.out.println("findActorsDistance failed. Please enter two different names.");
            return -1;
        }

        //finds out the start and end indices for the bfs
        for(int i = 0; i < hollywoodObjs.size(); i++)
        {
            Hollywood current = hollywoodObjs.get(i);

            if(current.getName().equals(a1))
            {
                startIndex = hollywoodObjs.indexOf(current);
            }
            else if(current.getName().equals(a2))
            {
                endIndex = hollywoodObjs.indexOf(current);
            }
        }

        //informs the client if the given name(s) can't be found in the data file
        if(startIndex == -1)
        {
            System.out.println("findActorsDistance failed. " + a1 + " can't be found in the given data.");
            return -1;
        }
        else if(endIndex == -1)
        {
            System.out.println("findActorsDistance failed. " + a2 + " can't be found in the given data.");
            return -1;
        }

        for(int vertexIndex = 0; vertexIndex < graph.getNumVertices(); vertexIndex++)
        {
            visited[vertexIndex] = false;
        }
        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty())
        {
            int currentIndex = traversalQueue.dequeue();
            Hollywood currentValue = hollywoodObjs.get(currentIndex);

            iter.add(currentValue);
            if(currentIndex == endIndex)
            {
                break;
            }
            for(int vertexIndex = 0; vertexIndex < graph.getNumVertices(); vertexIndex++)
            {
                Hollywood vertexValue = hollywoodObjs.get(vertexIndex);

                if(graph.isEdge(currentValue,vertexValue) && !visited[vertexIndex])
                {
                    traversalQueue.enqueue(vertexIndex);
                    visited[vertexIndex] = true;
                }
            }
        }

        int iterCount = 0;
        Hollywood current = iter.next();
        String s = "Path for reference:\n" + current + "\n";

        while(iter.hasNext())
        {
            Hollywood next = iter.next();
            boolean b1 = current.getType().equals("Movie");
            boolean b2 =  next.getType().equals("Actor");

            if(b1 && b2)
            {
                iterCount++;
                s += current + "\n" + next + "\n";
            }
            current = next;
        }
        System.out.println(s);
        return iterCount - 1;
    }

    /**
     * conducts our version of the Bechdel Test on the movies from the given data file. 
     * sets instance variable percentageOfFemale of Movies to the percentage of female supporting
     * roles in the cast, and passedTest of Movies that pass the test (over 50% of the 
     * supporting cast is female) to true and sets the variable percentageOfMoviesPassed 
     * of a HollywoodGraph object.
     */
    public void performBechdelTest()
    {
        int numOfMovies = 0;
        int numOfMoviesPassed = 0;

        for(int i = 0; i < hollywoodObjs.size(); i++)
        {
            Hollywood current = hollywoodObjs.get(i);

            if(current.getType().equals("Movie"))
            {
                Movie currentM = (Movie) current;
                LinkedList<Actor> currentCast = currentM.getCast();
                int currentCastSize = currentM.getCastSize();
                int numOfSupportingRoles = 0;
                int numOfFemale = 0;

                numOfMovies++;
                for(int j = 0; j < currentCastSize; j++)
                {
                    Actor currentA = currentCast.get(j);

                    if(currentA.getRole().equals("Supporting"))
                    {
                        numOfSupportingRoles++;
                        if(currentA.getGender().equals("Female"))
                        {
                            numOfFemale++;
                        }
                    }
                }
                float percentage = (float) numOfFemale/numOfSupportingRoles * 100;

                currentM.setPercentageOfFemale(percentage);
                if(percentage >= 50.0)
                {
                    currentM.setPassedTest(true);
                    numOfMoviesPassed++;
                }
                else
                {
                    currentM.setPassedTest(false);
                }
            }
        }
        this.percentageOfMoviesPassed = (float) numOfMoviesPassed/numOfMovies * 100;
    }

    /**
     * returns a LinkedList of all the Movies that have passed our Bechdel test and prints
     * out relevant information of each movie by calling getMovieInfo().
     * 
     * @return    a LinkedList of all the Movies that have passed. 
     */
    public LinkedList<Movie> findAllPassedMovies()
    {
        LinkedList<Movie> passedMovies = new LinkedList<Movie>();
        String s = "Here is a list of all the movies that have a supporting cast of more than 50% female:\n";
        boolean noOnePasses = true;

        for(int i = 0; i < hollywoodObjs.size(); i++)
        {
            Hollywood current = hollywoodObjs.get(i);

            if(current.getType().equals("Movie"))
            {
                Movie currentM = (Movie) current;

                if(currentM.getPassedTest())
                {
                    passedMovies.add(currentM);
                    s += currentM.getMovieInfo();
                    noOnePasses = false;
                }
            }
        }
        if(noOnePasses)
        {
            System.out.println("Unfortunately, no movie has passed our test.");
        }
        else
        {
            System.out.println(s);
        }
        return passedMovies;
    }

    /**
     * returns a String representation of a HollywoodGraph instance with all the movie 
     * titles and actor names, as well as the file it read from and the percentage of
     * movies in the file that passed our test. 
     * 
     * @return    a String representation
     */
    public String toString()
    {
        String s = "Read data from " + this.fileName + "\n";

        s += this.graph.toString();
        s += "Of all the movies in this file, " + this.percentageOfMoviesPassed + "% has passed our test.";
        return s;
    }

    public static void main(String[] args)
    {
        HollywoodGraph hg = new HollywoodGraph("nextBechdel_castGender.txt");
        //System.out.println("Testing constructor and createGraphFromFile()");
        //System.out.println(hg.graph);
        //System.out.println(hg);

        System.out.println("Testing findAllActors(The Jungle Book)");
        System.out.println("Expecting to return: BM,BK, IE, LN, SJ, GE, CW,NS, GS, BR, JF");
        LinkedList<Actor> actors = hg.findAllActors("The Jungle Book");
        System.out.println("Printing out returned list:\n" + actors);
        System.out.println();

        System.out.println("Testing findAllMovies(Jennifer Lawrence)");
        System.out.println("Expecting to return: Passengers, X-Men: Apocalypse");
        LinkedList<Movie> movies = hg.findAllMovies("Jennifer Lawrence");
        System.out.println("Printing out returned list:\n" + movies);
        System.out.println();

        System.out.println("Testing findActorsDistance(Megan Fox,Tyler Perry). Expecting 0; Megan Fox, Teenage Mutant Ninja Turtles: Out of the Shadows, Tyler Perry");
        System.out.println(hg.findActorsDistance("Megan Fox","Tyler Perry"));
        System.out.println();
        System.out.println("Testing findActorsDistance(Nick Arapoglou,Tyler Perry). Expecting 2; Nick Arapoglou, The Accountant, Robert Pralgo, The Divergent, Wilbur Fitzgerald, The Conjuring 2, Tyler Perry");
        System.out.println(hg.findActorsDistance("Nick Arapoglou","Tyler Perry"));
        System.out.println();

        System.out.println("Testing performBechdelTest() and findAllPassedMovies()");
        System.out.println("A movie will pass the test if its supporting cast is more than 50% female.");
        hg.performBechdelTest();
        System.out.println("Here is all the movies that passed the test:\n" + hg.findAllPassedMovies());

        System.out.println();
        System.out.println("Testing toString()");
        System.out.println(hg);
    }
}