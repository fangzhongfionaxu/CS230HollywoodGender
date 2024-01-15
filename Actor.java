import java.util.LinkedList;

/**
 * Write a description of class Actor here.
 *
 * @author Karrington W., Fiona X., and Wenhan X.
 * @version Dec 6, 2023
 */
public class Actor extends Hollywood
{
    protected String gender,role;
    protected LinkedList<Movie> played;

    /**
     * Constructor for objects of class Actor
     */
    public Actor(String name,String gender,String role, String type)
    {
        super(name,type);
        this.gender = gender;
        this.role = role;
        played = new LinkedList<Movie>();
    }

    // public boolean equals(Object obj)
    // {
        // Actor other = (Actor) obj;
        // if(this.name.equals(other.name))
        // {
            // return true;
        // }
        // return false;
    // }

    public String getGender()
    {
        return this.gender;
    }

    public String getRole()
    {
        return this.role;
    }

    public LinkedList<Movie> getPlayed()
    {
        return this.played;
    }

    public int getPlayedSize()
    {
        return played.size();
    }

    public void addPlayed(Hollywood movie)
    {
        if(!movie.type.equals("Movie"))
        {
            System.out.println("addPlayed failed. Input is not a Movie.");
            return;
        }
        Movie m = (Movie) movie;
        played.add(m);
    }

    public String getActorInfo()
    {
        String s = "\n" + super.toString() + ", " + this.gender + ", " + this.role;

        return s;
    }
}