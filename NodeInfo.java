import java.util.*;
import java.io.*;

// Class necessary for creating/holding ArrayList object
public class NodeInfo implements Serializable
{
    // ArrayList that stores every Node's info
    private ArrayList<String[]> meshData = new ArrayList<String[]>();

    // Update meshData ArrayList with new Node
    public void update( String[] nodeData )
    {
        meshData.add( nodeData );
    }

    // Return the size of the meshData ArrayList
    public int getSize()
    {
        return meshData.size();
    }

    // Return meshData ArrayList
    public String[] get( int index )
    {
        return meshData.get( index );
    }

    // Remove Node from ArrayList
    public void remove( int inPort )
    {
        for ( int i = 0; i < meshData.size(); i++ )
        {
            if ( Integer.parseInt( meshData.get( i )[2] ) == inPort )
            {
                meshData.remove( i );
            }
        }
    }

    // Check if Node is in meshData ArrayList
    public boolean inChatMesh( int inPort )
    {
        for ( int i = 0; i < meshData.size(); i++ )
        {
            if ( Integer.parseInt( meshData.get( i )[2] ) == inPort )
            {
                return true;
            }
        }
        return false;

    }

}
