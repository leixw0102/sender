package tv.icntv.sender.hdfs;/*
 * Copyright 2014 Future TV, Inc.
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.icntv.tv/licenses/LICENSE-1.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.icntv.sender.ISender;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/16
 * Time: 16:39
 */
public class ClientThread extends Thread{
    private Logger logger = LoggerFactory.getLogger("thread-hdfs");
    @Inject
    private ISender sender ;

    public ClientThread(){

    }
    @Override
    public void run() {
        long start=System.nanoTime();

//        HdfsDispatcher hdfsDispatcher = new HdfsDispatcher(lzoFile, hdfsUrl);
        try {
            if(sender.send()){
                logger.info("send hdfs success");
            };
            logger.info("use time = "+(System.nanoTime()-start)/Math.pow(10,9));
        } catch (Exception e){
            logger.error("error ,",e);
        }

    }

}
