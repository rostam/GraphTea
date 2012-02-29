// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.extensions.connectivity;

import org.idevlab.rjc.RedisNode;
import org.idevlab.rjc.SingleRedisOperations;
import org.idevlab.rjc.ds.DataSource;
import org.idevlab.rjc.ds.SimpleDataSource;
import org.idevlab.rjc.message.MessageListener;
import org.idevlab.rjc.message.RedisNodeSubscriber;

import graphlab.platform.core.BlackBoard;
import graphlab.platform.extension.BasicExtension;
import graphlab.platform.preferences.lastsettings.UserModifiableProperty;
import graphlab.plugins.commandline.Shell;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * Connects GraphLab to a Redis[http://redis.io/] server in order to have connectivitiy to more tools
 * and programming languages.
 * @author azin azadi
 */
public class Redis implements BasicExtension{
    private BlackBoard blackboard;

    @UserModifiableProperty (displayName = "Server Host")
    public static String DEFAULT_SERVER= "graphlab.sharif.ir";
//    public static String DEFAULT_PORT;
    private String server = DEFAULT_SERVER;
    private Jedis jedis;
    private Shell currentShell;

    public Redis(BlackBoard blackboard){
        this.blackboard = blackboard;
        DataSource dataSource = new SimpleDataSource(server);
        final SingleRedisOperations redis = new RedisNode(dataSource);

        final RedisNodeSubscriber subscriber = new RedisNodeSubscriber(dataSource);
        this.currentShell = Shell.getCurrentShell(blackboard);

        subscriber.subscribe("EVAL");
        subscriber.setMessageListener(new MessageListener() {
            public void onMessage(String channel, String message) {
                redis.publish("EVAL_RESULT", currentShell.evaluate(message) + "");
                
                //System.out.println(message);
            }
        });
        new Thread(){

			@Override
			public void run() {
		        subscriber.runSubscription();
			}
         
        }.start();
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
