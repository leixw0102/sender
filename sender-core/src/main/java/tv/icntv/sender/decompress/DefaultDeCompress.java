package tv.icntv.sender.decompress;/*
 * Copyright 2014 Future TV, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

import com.google.common.io.Closeables;
import com.google.inject.Inject;
import tv.icntv.sender.conf.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by leixw
 * <p/>
 * no need un compress
 * Author: leixw
 * Date: 2014/04/23
 * Time: 16:07
 */
public class DefaultDeCompress extends AbstractDeCompress {

    @Override
    public InputStream getInputStream(String source) throws IOException {
        File file = new File(source);
        if (!file.exists()) {
            throw new NullPointerException("file=" + file.getPath() + " not exist ;");
        }
        return new FileInputStream(file);
    }

    @Override
    public void close(InputStream in) {
        Closeables.closeQuietly(in);
    }

//    @Override
//    public void init() {
//        configuration.setAlias("UN_COMPRESS_NONE",this.getClass());
//    }
}
