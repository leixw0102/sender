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

import com.google.common.collect.Lists;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.icntv.sender.*;
import tv.icntv.sender.compress.Compress;
import tv.icntv.sender.compress.DefaultCompress;
import tv.icntv.sender.compress.LzoCompressImpl;
import tv.icntv.sender.conf.Configuration;
import tv.icntv.sender.decompress.DeCompress;
import tv.icntv.sender.decompress.DefaultDeCompress;
import tv.icntv.sender.decompress.GzDecompressImpl;
import tv.icntv.sender.hdfs.ClientThread;
import tv.icntv.sender.push.HdfsDispatcher;

import java.io.File;
import java.util.List;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/21
 * Time: 10:39
 * args[0]---source
 */
public class HdfsModule extends SenderModule implements HdfsProperties {
    private String[] args;

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public HdfsModule(String[] args) {
        this.args = args;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void configure(Binder binder) {
        binder.bind(Configuration.class).in(Scopes.SINGLETON);//in(Scopes.SINGLETON);
        File sourceFile = new File(args[0]);
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            logger.error("source ={} not exist or not file", args[0]);
            throw new RuntimeException("source =" + args[0] + " not exist or not file");
        }
        binder.bind(String.class).annotatedWith(Names.named("source")).toInstance(sourceFile.getPath());
        File lzo = new File(args[1]);
        if (!lzo.exists()) {
            lzo.mkdirs();
        }
        String fileName = sourceFile.getName();//sources.get(sources.size()-1);
        String lzoFileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".lzo";
        String lzoFile = lzo.getPath() + File.separator + lzoFileName;

        binder.bind(String.class).annotatedWith(Names.named("reCompressSource")).toInstance(lzoFile);
        binder.bind(String.class).annotatedWith(Names.named("hdfsUrl")).toInstance(args[2]);
        if (args.length == 5) {
            binder.bind(Compress.class).to(Type.valueOf(args[3]).getClassName()).in(Scopes.SINGLETON);
            binder.bind(DeCompress.class).to(Type.valueOf(args[4]).getClassName()).in(Scopes.SINGLETON);
        }else {
            binder.bind(Compress.class).to(DefaultCompress.class).in(Scopes.SINGLETON);
            binder.bind(DeCompress.class).to(DefaultDeCompress.class).in(Scopes.SINGLETON);
        }
        try {

            binder.bind(ReCompress.class).toConstructor(DefaultReCompress.class.getConstructor(String.class, String.class)).in(Scopes.SINGLETON);
            binder.bind(ISender.class).toConstructor(HdfsDispatcher.class.getConstructor(String.class)).in(Scopes.SINGLETON);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        binder.bind(Runnable.class).to(ClientThread.class).in(Scopes.SINGLETON);

    }

    @Override
    public List<String> getKeys() {
        return Lists.newArrayList("source.decompress", "target.compress");  //To change body of implemented methods use File | Settings | File Templates.
    }

    enum Type {
        UN_COMPRESS_NONE {
            @Override
            public Class getClassName() {
                return DefaultDeCompress.class;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }, GZ_DECOMPRESS {
            @Override
            public Class getClassName() {
                return GzDecompressImpl.class;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }, LZO_COMPRESS {
            @Override
            public Class getClassName() {
                return LzoCompressImpl.class;  //To change body of implemented methods use File | Settings | File Templates.
            }
        },NONE_COMPRESS {
            @Override
            public Class getClassName() {
                return DefaultCompress.class;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };

        public abstract Class getClassName();
    }


}
