package tv.icntv.sender;/*
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

import java.io.IOException;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/21
 * Time: 09:43
 */
public abstract class AbstractSender implements ISender{
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Inject
    private ReCompress reCompress;
    public AbstractSender(){

    }
    public AbstractSender(ReCompress reCompress){
        this.reCompress = reCompress;
    }

    public ReCompress getReCompress() {
        return reCompress;
    }

    public void setReCompress(ReCompress reCompress) {
        this.reCompress = reCompress;
    }

    @Override
    public boolean send() {
        logger.info("is reCompress:{},send target file:{}",reCompress.isReCompress()+"" ,reCompress.getSendFile());
        if(reCompress.isReCompress()){

            try {
                reCompress.reCompress();

            } catch (IOException e) {
                throw new RuntimeException("recompress error "+e); //To change body of catch statement use File | Settings | File Templates.
            }
        }
        try {
            sendMsg(reCompress.getSendFile());
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public abstract void sendMsg(String file)throws Exception;
}
