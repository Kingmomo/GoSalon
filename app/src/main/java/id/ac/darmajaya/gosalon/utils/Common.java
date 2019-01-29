package id.ac.darmajaya.gosalon.utils;

import java.util.ArrayList;
import java.util.List;

import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.GetTransaksi;
import id.ac.darmajaya.gosalon.Model.User.Auth;

public class Common {

    public static List<GetTransaksi> commonTransaksiList = new ArrayList<>();

    public static GetTransaksi findid(String id) {
        for(GetTransaksi pokemon : commonTransaksiList)
        {
            if(pokemon.getId().equals(id))
                return pokemon;
        }
        return null;
    }

}
