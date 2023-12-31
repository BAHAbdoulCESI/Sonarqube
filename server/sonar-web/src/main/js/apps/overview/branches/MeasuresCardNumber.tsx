/*
 * SonarQube
 * Copyright (C) 2009-2023 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
import { TextError } from 'design-system';
import * as React from 'react';
import { To } from 'react-router-dom';
import { formatMeasure } from '../../../helpers/measures';
import { MetricKey, MetricType } from '../../../types/metrics';
import { QualityGateStatusConditionEnhanced } from '../../../types/quality-gates';
import MeasuresCard from './MeasuresCard';

interface Props {
  failedConditions: QualityGateStatusConditionEnhanced[];
  label: string;
  url: To;
  value: string;
  failingConditionMetric: MetricKey;
  requireLabel: string;
  guidingKeyOnError?: string;
}

export default function MeasuresCardNumber(
  props: React.PropsWithChildren<Props & React.HTMLAttributes<HTMLDivElement>>,
) {
  const {
    label,
    value,
    failedConditions,
    url,
    failingConditionMetric,
    requireLabel,
    guidingKeyOnError,
    ...rest
  } = props;

  const failed = Boolean(
    failedConditions.find((condition) => condition.metric === failingConditionMetric),
  );

  return (
    <MeasuresCard
      url={url}
      value={formatMeasure(value, MetricType.ShortInteger)}
      metric={failingConditionMetric}
      label={label}
      failed={failed}
      data-guiding-id={failed ? guidingKeyOnError : undefined}
      {...rest}
    >
      {failed && <TextError className="sw-font-regular sw-mt-2" text={requireLabel} />}
    </MeasuresCard>
  );
}
