import java.util.*;
import java.io.*;

public class NodeInfo implements Serializable
{
    private ArrayList<String> meshData = new ArrayList<String[2]>();
    public void update(String[] nodeData)
    {
        meshData.add(nodeData);
    }
    public void remove(String nodeID)
    {
        for ( int i = 1; i < meshData.size(); i++ )
        {

        }
    }
    public int getSize()
    {
        return meshData.size();
    }
}
