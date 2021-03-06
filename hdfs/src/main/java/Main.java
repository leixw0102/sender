/*
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

import com.google.inject.*;
import tv.icntv.sender.HdfsModule;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/21
 * Time: 10:24
 */
public class Main {
    /**
     * args example: 0--source file;1-- compress path 2-- hdfs url 3--compress type 4 decompress
     * @param args
     */
    public static void main(String []args){
        HdfsModule module = new HdfsModule(args);
        Injector injector = Guice.createInjector(module);//(new String[]{"d:\\douban\\error.txt","d:\\",""}));

        Runnable client=injector.getInstance(Runnable.class);
        new Thread(client).start();
    }

}
