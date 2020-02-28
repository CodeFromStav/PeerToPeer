import java.util.*;

public class NodeInfo
{
    private ArrayList<Node> meshData = new ArrayList<Node>();
    public void update(Node inNode)
    {
        meshData.add(inNode);
    }
    public void remove(Node inNode)
    {
        meshData.remove(inNode);
    }
}
