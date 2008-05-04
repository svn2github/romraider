package com.romraider.ramtune.test.command.generator;

import com.romraider.io.protocol.Protocol;
import static com.romraider.util.ParamChecker.checkNotNullOrEmpty;
import static java.util.Arrays.asList;
import java.util.List;

public final class WriteCommandGenerator extends AbstractCommandGenerator {

    public WriteCommandGenerator(Protocol protocol) {
        super(protocol);
    }

    public List<byte[]> createCommands(byte[] data, byte[] address, int length) {
        checkNotNullOrEmpty(address, "address");
        checkNotNullOrEmpty(data, "data");
        return asList(protocol.constructWriteMemoryRequest(address, data));
    }

    public String toString() {
        return "Write";
    }
}
