package tv.icntv.sender.compress;/*
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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/17
 * Time: 10:42
 */
public abstract class AbstractCompress implements Compress{
    @Override
    public BufferedWriter getWriter(String target, String encode) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(getOutputStream(target),encode));  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close(BufferedWriter writer) {
        try {
            Closeables.close(writer, true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void close(OutputStream out) {
        try {
            Closeables.close(out, true);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     *
     */

    //public abstract void init();

    protected AbstractCompress() {
//        init();
    }
}
