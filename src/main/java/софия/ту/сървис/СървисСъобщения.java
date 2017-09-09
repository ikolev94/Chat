package софия.ту.сървис;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import софия.ту.модел.ЧатСъобщение;
import софия.ту.хранилище.ХранилищеСъобщения;

@Service
public class СървисСъобщения {

    @Autowired
    private ХранилищеСъобщения хранилищеСъобщения;

    public void save(ЧатСъобщение съобщение) {
	хранилищеСъобщения.save(съобщение);
    }

    public List<ЧатСъобщение> намериСъобщенияМеждуДвама(String изпращач, String получател) {
	return хранилищеСъобщения.findMessagesBetweenThoUsers(изпращач, получател);
    }
}
