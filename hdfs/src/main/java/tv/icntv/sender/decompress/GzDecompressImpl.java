package tv.icntv.sender.decompress;/*
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

import com.google.common.io.Closeables;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/27
 * Time: 11:13
 */
public class GzDecompressImpl extends AbstractDeCompress {
//    @Override
//    public void init() {
//        configuration.setAlias("GZ",this.getClass());
//    }

    @Override
    public InputStream getInputStream(String source) throws IOException {
        File file = new File(source);
        if (!file.exists()) {
            throw new NullPointerException("file=" + file.getPath() + " not exist ;");
        }
        return new GZIPInputStream(new FileInputStream(file));
    }


}
