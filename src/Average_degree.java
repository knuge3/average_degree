/**
 * Kyle Nugent
 * Coding Challenge
 * April 1st, 2016
 * Insight Fellowship Program
 */
package hashtag_graph;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import org.json.*;

public class Average_degree {
    
    public Date timestamp;
    private int nodes;
    private float vertex;
    public List<Node> graph;
    
    private Average_degree(){
        timestamp = null;
        nodes = 0;
        vertex = 0;
        graph = new ArrayList<Node>();
    }
    /**
     * Will check if a node is already in the graph with the same name
     * Will not insert and stop immediately if it finds one
     * @param n 
     */
    private void add_Node(Node n){
         for(int i = 0; i < graph.size(); i++){
             if(graph.get(i).name.equals(n.name)){
                 break;
             }
             else if(!graph.get(i).name.equals(n.name) && i == graph.size() -1){
                nodes +=1 ;
                graph.add(n);
             }
        }
    }
    private void remove_Node(Node n){
        if(graph.contains(n) == true){
            nodes -=1 ;
            graph.remove(n);
        }
    }
    /**
     * This is our function to find our average vertex before we
     * output it to a file
     */
    private void count(){
        int sum = 0;
        for(int i = 0; i < graph.size(); i++){
            sum += graph.get(i).get_Num_Edges();
        }
        vertex = sum/nodes;
    }
    private float get_Vertex(){
        return vertex;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Create the graph
        Average_degree twitter = new Average_degree();
        //Start the File read in process
        String filepath = args[1];
        String filepath2 = args[2];
        String line = new String();
        Date time;
        try {       
            //Create our file reader and our tokener
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            JSONTokener token = new JSONTokener(br);
            while(token.more()){    //Will continue until it reads false
                try{
                        //Will read in the next object from the file
                    JSONObject obj =new JSONObject(token.nextValue());  
                    //Convert the Twitter TimeStamp into something usable
                    final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
                    SimpleDateFormat sf = new SimpleDateFormat(TWITTER);
                    sf.setLenient(true);
                    time =sf.parse(obj.get("created_at").toString());
                    //Will Update the global timestamp
                    if (twitter.timestamp.before(time)){
                                twitter.timestamp = time;
                    }
                    List<String> edges = new ArrayList<String>();
                    JSONArray tags = obj.getJSONArray("hashtag");
                    //Grab all of the Tags and put them into a list
                    for(int y = 0; y < tags.length(); y++){
                        edges.add(tags.getJSONObject(y).toString());
                    }
                    //Create Nodes and edges based on the tags.
                    //If they already exist update the timestamps
                    for(int i = 0; i < edges.size(); i++){
                        twitter.add_Node(new Node(edges.get(i),time));               
                        for(int x = 0; x < edges.size(); x++){
                               if (x != i){
                                    twitter.graph.get(i).add_edge(new Edge(edges.get(x),time));
                               }          
                        }
                    }
                    /**Our cleanup starts here
                    * Cleanup will go through the nodes and check out the
                    * timestamps. If the timestamp is out of our 60 sec
                    * range, it will enter said node to see what edges
                    * have expired and remove them. If there are no more
                    * edges, the node is deleted as well.
                    **/
                    for(int t = 0; t < twitter.graph.size(); t++){
                        if(twitter.timestamp.getTime()-(twitter.graph.get(t).get_ttl().getTime()) > 60000){
                            Date lowest = twitter.timestamp;
                            for(int e = 0; e < twitter.graph.get(t).edges.size(); e++){
                                if(twitter.timestamp.getTime()-twitter.graph.get(t).edges.get(e).get_time().getTime() > 60000){
                                    twitter.graph.get(t).remove_edge(twitter.graph.get(t).edges.get(e));
                                }
                                else if(lowest.after(twitter.graph.get(t).edges.get(e).get_time())){
                                    lowest = twitter.graph.get(t).edges.get(e).get_time();
                                }
                            }
                                if(twitter.graph.get(t).get_Num_Edges() <=0){
                                    twitter.remove_Node(twitter.graph.get(t));
                                }
                                    else{
                                        twitter.graph.get(t).set_ttl(lowest);
                                    }
                                }
                            }
                        twitter.count();

                        try {  
                            //Write our average vertex to the out put file
                            PrintWriter out = new PrintWriter(new FileWriter("output.txt")); 
                            out.println(twitter.get_Vertex());
                        } catch(Exception e) {} 
                }catch (Exception e){}
            } 
        } catch (IOException e) {} 
    }
    
}
