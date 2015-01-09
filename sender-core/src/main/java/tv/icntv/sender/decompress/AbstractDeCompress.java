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
import com.google.inject.Inject;
import tv.icntv.sender.conf.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/17
 * Time: 10:46
 */
public abstract class AbstractDeCompress implements DeCompress {
    /**
     *
     */

    //public abstract void init();

    @Override
    public BufferedReader getBufferedReader(String source, String encoding) throws IOException {

        return new BufferedReader(new InputStreamReader(getInputStream(source),encoding));  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close(InputStream in) {
        Closeables.closeQuietly(in);
    }

    @Override
    public void close(BufferedReader in) {
        Closeables.closeQuietly(in);
    }

    protected AbstractDeCompress() {
//        init();
    }
}
