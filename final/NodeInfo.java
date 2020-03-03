import java.util.*;
import java.io.*;

public class NodeInfo implements Serializable
{
    private ArrayList<String[]> meshData = new ArrayList<String[]>();
    public void update(String[] nodeData)
    {
        meshData.add(nodeData);
    }
    public void remove(String nodeID)
    {
        for ( int i = 0; i < meshData.size(); i++ )
        {
            if ( meshData.get(i)[0] == nodeID )
            {
                meshData.remove( i );
            }
        }
    }
    public int getSize()
    {
        return meshData.size();
    }
    public String[] get(int index)
    {
        return meshData.get(index);
    }
    public String nodeInfoToString()
    {
        String infoToString = " ";
        for (int i = 0; i < meshData.size(); i++)
        {
            infoToString += "{";
            for (int j = 0; j < 3; j++)
            {
                switch(j)
                {
                    case 0:
                        infoToString += "id: " + meshData.get(i)[j] + ", ";
                        break;
                    case 1:
                        infoToString += "ip: " + meshData.get(i)[j]  + ", ";
                        break;
                    case 2:
                        infoToString += "port: " + meshData.get(i)[j]  + "";
                        break;
                }
            }
            infoToString += "}";
        }
        return "Current NodeInfo is " + infoToString;
    }

}
