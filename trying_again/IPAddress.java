import java.io.*;
class IPAddress
{

    private int value;

    public IPAddress(int value)
    {
        this.value = value;
    }

    public IPAddress(String stringValue)
    {
        String[] parts = stringValue.split("\\.");
        if( parts.length != 4 )
        {
            throw new IllegalArgumentException();
        }
        value =
                (Integer.parseInt(parts[0], 10) << (8*3)) & 0xFF000000 |
                (Integer.parseInt(parts[1], 10) << (8*2)) & 0x00FF0000 |
                (Integer.parseInt(parts[2], 10) << (8*1)) & 0x0000FF00 |
                (Integer.parseInt(parts[3], 10) << (8*0)) & 0x000000FF;
    }

    public int getOctet(int i)
    {

        if( i<0 || i>=4 ) throw new IndexOutOfBoundsException();

        return (value >> (i*8)) & 0x000000FF;
    }

    public void incrementIP() {
        value++;
    }
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for(int i=3; i>=0; --i)
        {
            sb.append(getOctet(i));
            if( i!= 0) sb.append(".");
        }

        return sb.toString();
    }
}
