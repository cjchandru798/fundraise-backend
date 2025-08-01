package com.example.fundraise.service;

import com.example.fundraise.dto.MilestoneDTO;
import com.example.fundraise.entity.Milestone;
import com.example.fundraise.repository.MilestoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public void addMilestone(MilestoneDTO dto) {
        Milestone m = new Milestone(null, dto.getAmount(), dto.getLabel(), dto.getIcon());
        milestoneRepository.save(m);
    }

    public void deleteMilestone(Long id) {
        milestoneRepository.deleteById(id);
    }

    public List<Milestone> getAllMilestones() {
        return milestoneRepository.findAll();
    }
}
