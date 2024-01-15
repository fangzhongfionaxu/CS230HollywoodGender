
/**
 * Write a description of class Hollywood here.
 *
 * @author Karrington Wilson, Fiona Xu, Wenhan Xue
 * @version Dec 12, 2023
 */
public class Hollywood
{
    protected String name,type;

    /**
     * Constructor for objects of class Hollywood
     */
    public Hollywood(String name,String type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return this.name;
    }

    public String getType()
    {
        return this.type;
    }

    public boolean equals(Object obj)
    {
        Hollywood other = (Hollywood) obj;

        if(this.name.equals(other.name) && this.type.equals(other.type))
        {
            return true;
        }
        return false;
    }

    public String toString()
    {
        return this.type + " " + this.name;
    }
}
