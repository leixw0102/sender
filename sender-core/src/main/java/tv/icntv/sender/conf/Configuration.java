package tv.icntv.sender.conf;/*
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

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import tv.icntv.sender.utils.PropertiesUtils;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/21
 * Time: 09:51
 */
public class Configuration {

    private static Map<Object, Object> maps = Maps.newHashMap();
    private static Map<String,String> aliasMaps=Maps.newConcurrentMap();

    public static Map<Object, Object> getMaps() {
        return maps;
    }

    public static Map<String, String> getAliasMaps() {
        return aliasMaps;
    }

    public static void setAliasMaps(Map<String, String> aliasMaps) {
        Configuration.aliasMaps = aliasMaps;
    }

    public static void setMaps(Map<Object, Object> maps) {
        Configuration.maps = maps;
    }

    public Configuration(){

    }

    public void addResource(String file){
        Preconditions.checkNotNull(file,"resource file null");
        Properties pro = PropertiesUtils.getProperties(file);
        maps.putAll(pro);
    }

    public void setAlias(String alias,Class tClass){
        Preconditions.checkNotNull(alias,"alias null");
        aliasMaps.put(alias,tClass.getName());
    }

    public Configuration(String file){
        this.addResource(file);
    }
    public void setConf(String key, String value){
        Preconditions.checkNotNull(key," key null");
        Preconditions.checkNotNull(value,"value null");
        if(aliasMaps.containsKey(value)){
            maps.put(key,aliasMaps.get(value));
            return;
        }
        maps.put(key,value);
    }

    public Object getConf(String key){
        Preconditions.checkNotNull(key,"key null");
        return maps.get(key);
    }

    public void setConf(Map<String,String> maps){
        Set<String> keys = maps.keySet();
        for( String key : keys){
            setConf(key,maps.get(key));
        }
    }
}
