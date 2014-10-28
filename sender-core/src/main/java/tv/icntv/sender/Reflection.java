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

import com.google.common.base.Strings;
import tv.icntv.sender.conf.Configuration;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/17
 * Time: 09:53
 */
public class Reflection {

    public static Object newInstance(String key,Configuration configuration) throws Exception {
        return newInstance(configuration.getConf(key).toString());
    }

    /**
     *
     * @param cName
     * @return
     * @throws Exception
     */
    public static Object newInstance(String cName) throws  Exception{
        if(Strings.isNullOrEmpty(cName)){
            throw new NullPointerException("parameter "+cName+" null");
        }

         try{
            return Class.forName(cName).newInstance()  ;
         }catch (Exception e){
             throw e;
         }

    }

}
