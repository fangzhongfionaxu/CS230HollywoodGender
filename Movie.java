import java.util.LinkedList;

/**
 * Create an object of type Movie, with its title, cast, percentage of female supporting
 * roles, and whether it has passed our bechdel test. a child of Hollywood.
 *
 * @author Karrington W., Fiona X., and Wenhan X.
 * @version Dec 6, 2023
 */
public class Movie extends Hollywood
{
    protected LinkedList<Actor> cast;
    protected boolean passedTest;
    protected float percentageOfFemale;

    /**
     * Calls the parent constructor and instantiates the cast LinkedList. 
     */
    public Movie(String name, String type)
    {
        super(name,type);
        cast = new LinkedList<Actor>();
    }

    /**
     * gets the cast LinkedList of this Movie
     * 
     * @return    the cast
     */
    public LinkedList<Actor> getCast()
    {
        return this.cast;
    }

    /**
     * gets the number of actors in this Movie
     * 
     * @return    number of actors 
     */
    public int getCastSize()
    {
        return cast.size();
    }

    /**
     * gets the name of actors in this Movie
     * 
     * @return    a LinkedList of the names
     */
    public LinkedList<String> getCastNames()
    {
        LinkedList<String> castNames = new LinkedList<String>();

        for(int i = 0; i < cast.size(); i++)
        {
            castNames.add(cast.get(i).getName());
        }
        return castNames;
    }

    /**
     * gets whether this Movie has passed the test
     * 
     * @return    a boolean value. true if passed and false otherwise.
     */
    public boolean getPassedTest()
    {
        return passedTest;
    }

    /**
     * adds an Actor object to the cast LinkedList of this movie
     * 
     * @param  actor  the Actor object to add
     */
    public void addCast(Hollywood actor)
    {
        if(!actor.type.equals("Actor"))
        {
            System.out.println("addCast failed. Input is not an Actor.");
            return;
        }
        Actor a = (Actor) actor;
        cast.add(a);
    }

    /**
     * sets the boolean value of the instance variable passedTest of this Movie
     * 
     * @param  pf  whether the Movie has passed
     */
    public void setPassedTest(boolean pf)
    {
        this.passedTest = pf;
    }

    /**
     * stes instance variable indicating the percentage of female supporting roles in 
     * this Movie
     * 
     * @param  percentage   the percentage of female supporting roles
     */
    public void setPercentageOfFemale(float percentage)
    {
        this.percentageOfFemale = percentage;
    }

    /**
     * returns a more detailed String representation of a Movie, with its title, cast, its 
     * percentage of female supporting roles, and whether or not it has passed the test.
     * 
     * @return    a String representation
     */
    public String getMovieInfo()
    {
        String s = super.toString() + " has a supporting cast of " + this.percentageOfFemale + "% Female, ";

        if(passedTest)
        {
            s += "passing our test,";
        }
        else
        {
            s += "failing our test,";
        }

        s += " and stars the following " + this.getCastSize() + " actor(s):";

        for(int i = 0; i < this.getCastSize(); i++)
        {
            s += cast.get(i);
        }

        return "\n" + s;
    }
}