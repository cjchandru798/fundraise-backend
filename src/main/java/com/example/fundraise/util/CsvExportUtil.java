package com.example.fundraise.util;

import com.example.fundraise.entity.Donation;
import com.example.fundraise.entity.Intern;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.List;

@Component
public class CsvExportUtil {

    public void writeInternCsv(PrintWriter writer, List<Intern> interns) {
        writer.println("ID,Name,Email,College,City,Referral Code,Total Raised");

        for (Intern i : interns) {
            int total = i.getDonations().stream().mapToInt(Donation::getAmount).sum();
            writer.printf("%d,%s,%s,%s,%s,%s,%d%n",
                    i.getId(), i.getName(), i.getEmail(), i.getCollege(), i.getCity(), i.getReferralCode(), total
            );
        }
    }

    public void writeDonationCsv(PrintWriter writer, List<Donation> donations) {
        writer.println("ID,Donor Name,Amount,Date,Intern Name");

        for (Donation d : donations) {
            writer.printf("%d,%s,%d,%s,%s%n",
                    d.getId(),
                    d.getDonorName() == null ? "" : d.getDonorName(),
                    d.getAmount(),
                    d.getDate(),
                    d.getIntern().getName()
            );
        }
    }
}
