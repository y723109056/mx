package com.mx.util;

public class DatatypeConverterUtil {
	
	private static final byte[] decodeMap = initDecodeMap();
    private static final byte PADDING = 127;

    private static byte[] initDecodeMap() {
        byte[] map = new byte[128];
        int i;
        for (i = 0; i < 128; i++) {
            map[i] = -1;
        }

        for (i = 'A'; i <= 'Z'; i++) {
            map[i] = (byte) (i - 'A');
        }
        for (i = 'a'; i <= 'z'; i++) {
            map[i] = (byte) (i - 'a' + 26);
        }
        for (i = '0'; i <= '9'; i++) {
            map[i] = (byte) (i - '0' + 52);
        }
        map['+'] = 62;
        map['/'] = 63;
        map['='] = PADDING;

        return map;
    }

	public static byte[] parseBase64Binary(String text) {
        final int buflen = guessLength(text);
        final byte[] out = new byte[buflen];
        int o = 0;

        final int len = text.length();
        int i;

        final byte[] quadruplet = new byte[4];
        int q = 0;

        // convert each quadruplet to three bytes.
        for (i = 0; i < len; i++) {
            char ch = text.charAt(i);
            byte v = decodeMap[ch];

            if (v != -1) {
                quadruplet[q++] = v;
            }

            if (q == 4) {
                // quadruplet is now filled.
                out[o++] = (byte) ((quadruplet[0] << 2) | (quadruplet[1] >> 4));
                if (quadruplet[2] != PADDING) {
                    out[o++] = (byte) ((quadruplet[1] << 4) | (quadruplet[2] >> 2));
                }
                if (quadruplet[3] != PADDING) {
                    out[o++] = (byte) ((quadruplet[2] << 6) | (quadruplet[3]));
                }
                q = 0;
            }
        }

        if (buflen == o) // speculation worked out to be OK
        {
            return out;
        }

        // we overestimated, so need to create a new buffer
        byte[] nb = new byte[o];
        System.arraycopy(out, 0, nb, 0, o);
        return nb;
    }
	
	private static int guessLength(String text) {
        final int len = text.length();

        // compute the tail '=' chars
        int j = len - 1;
        for (; j >= 0; j--) {
            byte code = decodeMap[text.charAt(j)];
            if (code == PADDING) {
                continue;
            }
            if (code == -1) // most likely this base64 text is indented. go with the upper bound
            {
                return text.length() / 4 * 3;
            }
            break;
        }

        j++;    // text.charAt(j) is now at some base64 char, so +1 to make it the size
        int padSize = len - j;
        if (padSize > 2) // something is wrong with base64. be safe and go with the upper bound
        {
            return text.length() / 4 * 3;
        }

        // so far this base64 looks like it's unindented tightly packed base64.
        // take a chance and create an array with the expected size
        return text.length() / 4 * 3 - padSize;
    }
	
}
