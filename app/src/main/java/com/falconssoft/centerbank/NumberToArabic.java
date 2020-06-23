package com.falconssoft.centerbank;

public class  NumberToArabic {

    NumberToArabic() {
    }

    static String getArabicString(String amount) {
        String[] st = amount.split("\\.", -1);
        String Amount = st[0];
        if (Amount.length() == 5 || Amount.length() == 2) {
            Amount = "0" + Amount;
        }
        if (Amount.length() == 4 || Amount.length() == 1) {
            Amount = "00" + Amount;
        }
        try {
            if (st[1].length() == 1) {
                st[1] = st[1] + "00";
            }
            if (st[1].length() == 2) {
                st[1] = st[1] + "0";
            }
            if (st[1].length() == 0) {
                st[1] = "000";
            }
            if (st[1].substring(0, 2).equals("00")) {
                st[1] = st[1].substring(2);
            } else if (st[1].charAt(0) == '0') {
                st[1] = st[1].substring(1);
            }
        } catch (Exception e) {
        }
        String amountTxt = "";
        if (Amount.length() == 7) {
            amountTxt = "";
            if (Amount.length() == 7) {
                switch (Amount.charAt(0)) {
                    case '1':
                        amountTxt = "مليون ";
                        break;
                    case '2':
                        amountTxt = "مليونا ";
                        break;
                    case '3':
                        amountTxt = "ثلاثة ملايين ";
                        break;
                    case '4':
                        amountTxt = "أربع ملايين ";
                        break;
                    case '5':
                        amountTxt = "خمس ملايين ";
                        break;
                    case '6':
                        amountTxt = "ست ملايين ";
                        break;
                    case '7':
                        amountTxt = "سبع ملايين ";
                        break;
                    case '8':
                        amountTxt = "ثمان ملايين ";
                        break;
                    case '9':
                        amountTxt = "تسع ملايين ";
                        break; 
                }
            }
            if (Integer.valueOf(Amount.substring(4)).intValue() > 0) {
                if (Integer.valueOf(Amount.substring(1,4)).intValue() > 0) {
                    amountTxt = amountTxt + "و " + get6deg(Amount.substring(1, 4)) + " و " + get3deg(Amount.substring(4));
                }else{
                    amountTxt = amountTxt   + " و " + get3deg(Amount.substring(4));

                }
            } else {
                if (Integer.valueOf(Amount.substring(1,4)).intValue() > 0) {
                    amountTxt = amountTxt + "و " + get6deg(Amount.substring(1, 4)) + get3deg(Amount.substring(4));
                }else{
                    amountTxt = amountTxt  + get3deg(Amount.substring(4));
                }
            }
        } else if (Amount.length() == 6) {
            if (Integer.valueOf(Amount.substring(3)).intValue() > 0) {
                amountTxt = get6deg(Amount.substring(0, 3)) + " و " + get3deg(Amount.substring(3));
            } else {
                amountTxt = get6deg(Amount.substring(0, 3)) + get3deg(Amount.substring(3));
            }
        } else if (Amount.length() == 3) {
            amountTxt = get3deg(Amount);
        }
        try {
            if (Integer.valueOf(st[1]).intValue() > 0) {
                amountTxt = amountTxt + " و " + st[1] + " فلساً";
            }
        } catch (Exception e2) {
        }
        return amountTxt.replace("الفاً دينار", "الف دينار").replace("مئتان دينار", "مئتا دينار").replace("الفان دينار", "الفا دينار").replace("مئتان الف", "مئتا الف");
    }

    private static String get3deg(String Amount) {
        String txt = "";
        if (Amount.charAt(0) == '1') {
            txt = "مائة";
        } else if (Amount.charAt(0) == '2') {
            txt = "مئتان";
        } else if (Amount.charAt(0) == '3') {
            txt = "ثلاثمائة";
        } else if (Amount.charAt(0) == '4') {
            txt = "اربعمائة";
        } else if (Amount.charAt(0) == '5') {
            txt = "خمسمائة";
        } else if (Amount.charAt(0) == '6') {
            txt = "ستمائة";
        } else if (Amount.charAt(0) == '7') {
            txt = "سبعمائة";
        } else if (Amount.charAt(0) == '8') {
            txt = "ثمانمائة";
        } else if (Amount.charAt(0) == '9') {
            txt = "تسعمائة";
        }
        if (Amount.charAt(1) == '0') {
            if (!(Amount.charAt(0) == '0' || Amount.charAt(2) == '0')) {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + " دينار";
            }
            if (Amount.charAt(2) == '1') {
                txt = txt + "واحد دينار";
            } else if (Amount.charAt(2) == '2') {
                txt = txt + "اثنان دينار";
            } else if (Amount.charAt(2) == '3') {
                txt = txt + "ثلاثة دنانير";
            } else if (Amount.charAt(2) == '4') {
                txt = txt + "اربعة دنانير";
            } else if (Amount.charAt(2) == '5') {
                txt = txt + "خمسة دنانير";
            } else if (Amount.charAt(2) == '6') {
                txt = txt + "ستة دنانير";
            } else if (Amount.charAt(2) == '7') {
                txt = txt + "سبعة دنانير";
            } else if (Amount.charAt(2) == '8') {
                txt = txt + "ثمانية دنانير";
            } else if (Amount.charAt(2) == '9') {
                txt = txt + "تسعة دنانير";
            }
        } else if (Amount.charAt(1) == '1') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "عشر دنانير";
            }
            if (Amount.charAt(2) == '1') {
                txt = txt + "احد عشر ديناراً";
            } else if (Amount.charAt(2) == '2') {
                txt = txt + "اثنا عشر ديناراً";
            } else if (Amount.charAt(2) == '3') {
                txt = txt + "ثلاثة عشر ديناراً";
            } else if (Amount.charAt(2) == '4') {
                txt = txt + "اربعة عشر ديناراً";
            } else if (Amount.charAt(2) == '5') {
                txt = txt + "خمسة عشر ديناراً";
            } else if (Amount.charAt(2) == '6') {
                txt = txt + "ستة عشر ديناراً";
            } else if (Amount.charAt(2) == '7') {
                txt = txt + "سبعة عشر ديناراً";
            } else if (Amount.charAt(2) == '8') {
                txt = txt + "ثمانية عشر ديناراً";
            } else if (Amount.charAt(2) == '9') {
                txt = txt + "تسعة عشر ديناراً";
            }
        } else if (Amount.charAt(1) == '2') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "عشرون ديناراً";
            }
            if (Amount.charAt(2) == '1') {
                txt = txt + "واحد و عشرون ديناراً";
            } else if (Amount.charAt(2) == '2') {
                txt = txt + "اثنان و عشرون ديناراً";
            } else if (Amount.charAt(2) == '3') {
                txt = txt + "ثلاثة و عشرون ديناراً";
            } else if (Amount.charAt(2) == '4') {
                txt = txt + "اربعة و عشرون ديناراً";
            } else if (Amount.charAt(2) == '5') {
                txt = txt + "خمسة و عشرون ديناراً";
            } else if (Amount.charAt(2) == '6') {
                txt = txt + "ستة و عشرون ديناراً";
            } else if (Amount.charAt(2) == '7') {
                txt = txt + "سبعة و عشرون ديناراً";
            } else if (Amount.charAt(2) == '8') {
                txt = txt + "ثمانية و عشرون ديناراً";
            } else if (Amount.charAt(2) == '9') {
                txt = txt + "تسعة و عشرون ديناراً";
            }
        }
        if (Amount.charAt(1) == '3') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و ثلاثون ديناراً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و ثلاثون ديناراً";
            }
            return txt;
        } else if (Amount.charAt(1) == '4') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "اربعون ديناراً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و اربعون ديناراً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و اربعون ديناراً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و اربعون ديناراً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و اربعون ديناراً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و اربعون ديناراً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و اربعون ديناراً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و اربعون ديناراً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و اربعون ديناراً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و اربعون ديناراً";
            }
            return txt;
        } else if (Amount.charAt(1) == '5') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "خمسون ديناراً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و خمسون ديناراً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و خمسون ديناراً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و خمسون ديناراً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و خمسون ديناراً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و خمسون ديناراً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و خمسون ديناراً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و خمسون ديناراً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و خمسون ديناراً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و خمسون ديناراً";
            }
            return txt;
        } else if (Amount.charAt(1) == '6') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "ستون ديناراً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و ستون ديناراً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و ستون ديناراً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و ستون ديناراً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و ستون ديناراً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و ستون ديناراً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و ستون ديناراً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و ستون ديناراً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و ستون ديناراً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و ستون ديناراً";
            }
            return txt;
        } else if (Amount.charAt(1) == '7') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "سبعون ديناراً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و سبعون ديناراً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و سبعون ديناراً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و سبعون ديناراً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و سبعون ديناراً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و سبعون ديناراً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و سبعون ديناراً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و سبعون ديناراً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و سبعون ديناراً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و سبعون ديناراً";
            }
            return txt;
        } else if (Amount.charAt(1) == '8') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و ثمانون ديناراً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و ثمانون ديناراً";
            }
            return txt;
        } else if (Amount.charAt(1) != '9') {
            return txt;
        } else {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "تسعون ديناراً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و تسعون ديناراً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و تسعون ديناراً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و تسعون ديناراً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و تسعون ديناراً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و تسعون ديناراً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و تسعون ديناراً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و تسعون ديناراً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و تسعون ديناراً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و تسعون ديناراً";
            }
            return txt;
        }
    }

    private static String get6deg(String Amount) {
        String txt = "";
        if (Amount.charAt(0) == '1') {
            txt = "مائة";
        } else if (Amount.charAt(0) == '2') {
            txt = "مئتان";
        } else if (Amount.charAt(0) == '3') {
            txt = "ثلاثمائة";
        } else if (Amount.charAt(0) == '4') {
            txt = "اربعمائة";
        } else if (Amount.charAt(0) == '5') {
            txt = "خمسمائة";
        } else if (Amount.charAt(0) == '6') {
            txt = "ستمائة";
        } else if (Amount.charAt(0) == '7') {
            txt = "سبعمائة";
        } else if (Amount.charAt(0) == '8') {
            txt = "ثمانمائة";
        } else if (Amount.charAt(0) == '9') {
            txt = "تسعمائة";
        }
        if (Amount.charAt(1) == '0') {
            if (!(Amount.charAt(0) == '0' || Amount.charAt(2) == '0')) {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + " الف";
            }
            if (Amount.charAt(2) == '1') {
                txt = txt + "الف";
            } else if (Amount.charAt(2) == '2') {
                txt = txt + "الفان";
            } else if (Amount.charAt(2) == '3') {
                txt = txt + "ثلاثة آلاف";
            } else if (Amount.charAt(2) == '4') {
                txt = txt + "اربعة آلاف";
            } else if (Amount.charAt(2) == '5') {
                txt = txt + "خمسة آلاف";
            } else if (Amount.charAt(2) == '6') {
                txt = txt + "ستة آلاف";
            } else if (Amount.charAt(2) == '7') {
                txt = txt + "سبعة آلاف";
            } else if (Amount.charAt(2) == '8') {
                txt = txt + "ثمانية آلاف";
            } else if (Amount.charAt(2) == '9') {
                txt = txt + "تسعة آلاف";
            }
        } else if (Amount.charAt(1) == '1') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "عشرة آلاف";
            }
            if (Amount.charAt(2) == '1') {
                txt = txt + "احد عشر الفاً";
            } else if (Amount.charAt(2) == '2') {
                txt = txt + "اثنا عشر الفاً";
            } else if (Amount.charAt(2) == '3') {
                txt = txt + "ثلاثة عشر الفاً";
            } else if (Amount.charAt(2) == '4') {
                txt = txt + "اربعة عشر الفاً";
            } else if (Amount.charAt(2) == '5') {
                txt = txt + "خمسة عشر الفاً";
            } else if (Amount.charAt(2) == '6') {
                txt = txt + "ستة عشر الفاً";
            } else if (Amount.charAt(2) == '7') {
                txt = txt + "سبعة عشر الفاً";
            } else if (Amount.charAt(2) == '8') {
                txt = txt + "ثمانية عشر الفاً";
            } else if (Amount.charAt(2) == '9') {
                txt = txt + "تسعة عشر الفاً";
            }
        } else if (Amount.charAt(1) == '2') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "عشرون الفاً";
            }
            if (Amount.charAt(2) == '1') {
                txt = txt + "واحد و عشرون الفاً";
            } else if (Amount.charAt(2) == '2') {
                txt = txt + "اثنان و عشرون الفاً";
            } else if (Amount.charAt(2) == '3') {
                txt = txt + "ثلاثة و عشرون الفاً";
            } else if (Amount.charAt(2) == '4') {
                txt = txt + "اربعة و عشرون الفاً";
            } else if (Amount.charAt(2) == '5') {
                txt = txt + "خمسة و عشرون الفاً";
            } else if (Amount.charAt(2) == '6') {
                txt = txt + "ستة و عشرون الفاً";
            } else if (Amount.charAt(2) == '7') {
                txt = txt + "سبعة و عشرون الفاً";
            } else if (Amount.charAt(2) == '8') {
                txt = txt + "ثمانية و عشرون الفاً";
            } else if (Amount.charAt(2) == '9') {
                txt = txt + "تسعة و عشرون الفاً";
            }
        }
        if (Amount.charAt(1) == '3') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + "ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و ثلاثون الفاً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و ثلاثون الفاً";
            }
            return txt;
        } else if (Amount.charAt(1) == '4') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + " اربعون الفاً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و اربعون الفاً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و اربعون الفاً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و اربعون الفاً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و اربعون الفاً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و اربعون الفاً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و اربعون الفاً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و اربعون الفاً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و اربعون الفاً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و اربعون الفاً";
            }
            return txt;
        } else if (Amount.charAt(1) == '5') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + " خمسون الفاً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و خمسون الفاً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و خمسون الفاً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و خمسون الفاً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و خمسون الفاً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و خمسون الفاً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و خمسون الفاً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و خمسون الفاً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و خمسون الفاً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و خمسون الفاً";
            }
            return txt;
        } else if (Amount.charAt(1) == '6') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + " ستون الفاً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و ستون الفاً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و ستون الفاً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و ستون الفاً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و ستون الفاً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و ستون الفاً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و ستون الفاً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و ستون الفاً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و ستون الفاً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و ستون الفاً";
            }
            return txt;
        } else if (Amount.charAt(1) == '7') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + " سبعون الفاً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و سبعون الفاً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و سبعون الفاً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و سبعون الفاً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و سبعون الفاً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و سبعون الفاً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و سبعون الفاً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و سبعون الفاً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و سبعون الفاً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و سبعون الفاً";
            }
            return txt;
        } else if (Amount.charAt(1) == '8') {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + " ثمانون الفاً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و ثمانون الفاً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و ثمانون الفاً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و ثمانون الفاً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و ثمانون الفاً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و ثمانون الفاً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و ثمانون الفاً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و ثمانون الفاً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و ثمانون الفاً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و ثمانون الفاً";
            }
            return txt;
        } else if (Amount.charAt(1) != '9') {
            return txt;
        } else {
            if (Amount.charAt(0) != '0') {
                txt = txt + " و ";
            }
            if (Amount.charAt(2) == '0') {
                txt = txt + " تسعون الفاً";
            }
            if (Amount.charAt(2) == '1') {
                return txt + "واحد و تسعون الفاً";
            }
            if (Amount.charAt(2) == '2') {
                return txt + "اثنان و تسعون الفاً";
            }
            if (Amount.charAt(2) == '3') {
                return txt + "ثلاثة و تسعون الفاً";
            }
            if (Amount.charAt(2) == '4') {
                return txt + "اربعة و تسعون الفاً";
            }
            if (Amount.charAt(2) == '5') {
                return txt + "خمسة و تسعون الفاً";
            }
            if (Amount.charAt(2) == '6') {
                return txt + "ستة و تسعون الفاً";
            }
            if (Amount.charAt(2) == '7') {
                return txt + "سبعة و تسعون الفاً";
            }
            if (Amount.charAt(2) == '8') {
                return txt + "ثمانية و تسعون الفاً";
            }
            if (Amount.charAt(2) == '9') {
                return txt + "تسعة و تسعون الفاً";
            }
            return txt;
        }
        }
}


